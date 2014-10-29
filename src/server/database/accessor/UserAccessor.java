/**
 * 
 */
package server.database.accessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import server.database.DatabaseAccessor;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class UserAccessor extends DatabaseAccessor
{
	private Connection conn;
	private Statement stmt;
	
	
	public UserAccessor(Connection c) throws SQLException
	{
		this.conn = c;
		stmt = conn.createStatement();
	}
	
	public UserAccessor()
	{
		
	}
	
	
	public boolean addNewUser(User u)
	{
		try
		{
			conn = databaseConnectionPool.getCpds().getConnection();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to get connection");
			return false;
		}
		try
		{
			stmt = conn.createStatement();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to create statement");
			return false;
		}
		
		boolean result = false;
		StringBuilder input = new StringBuilder();
		
		input.append("\'" + u.getUsername() + "\',\'");
		input.append(u.getPassword() + "\',\'");
		input.append(u.getFirstname() + "\',\'");
		input.append(u.getLastname() + "\',\'");
		input.append(u.getEmail() + "\'");
		
		String sql = "INSERT INTO User (Username, Password, FirstName, LastName, Email) " +
						"VALUES (" + input.toString() + ");";
		
		
		
		try
		{
			stmt.execute(sql);
			result = true;
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to insert User: " + u.toString());
		}
		
		try
		{
			stmt.close();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to close statement: " + sql);
//			e.printStackTrace();
		}
		try
		{
			
			conn.close();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to close connection: " + conn);
//			e.printStackTrace();
		}
		
		return result;
	}
	
	
}
