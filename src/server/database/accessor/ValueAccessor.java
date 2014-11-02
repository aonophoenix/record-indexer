/**
 * 
 */
package server.database.accessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.database.DatabaseAccessor;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class ValueAccessor extends DatabaseAccessor
{
	private Connection conn;
	private Statement stmt;
	
	public ValueAccessor(Connection c) throws SQLException
	{
		this.conn = c;
		stmt = conn.createStatement();
	}
	
	public ValueAccessor()
	{
		
	}
	
	public boolean addNewValue(String n, String v, int recordKey)
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
		String sql = null;
		
		input.append(recordKey + ",\'");
		input.append(n + "\',\'");
		input.append(v + "\'");
		
		sql = "INSERT INTO Value (RecordKey, Name, Value) " +
				"VALUES (" + input.toString() + ");";
		
		try
		{
			stmt.execute(sql);
			result = true;
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to insert Name-Value Pair: " + n + "-" + v);
		}
		
		try
		{
			stmt.close();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to close statement: " + sql);
		}
		try
		{
			
			conn.close();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to close connection: " + conn);
		}
		
		return result;
	}
}
