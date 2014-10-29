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
public class ImageAccessor extends DatabaseAccessor
{
	private Connection conn;
	private Statement stmt;
	private final int COMPLETE = 1;
	private final int INCOMPLETE = 0;
	
	
	
	public ImageAccessor(Connection c) throws SQLException
	{
		this.conn = c;
		stmt = conn.createStatement();
	}
	
	public ImageAccessor()
	{
		
	}
	
	public boolean addNewImage(Image i, int projectKey)
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
		
		input.append("\'" + i.getFileString() + "\',");
		input.append(projectKey + ",");
		
		if (i.getIsComplete())
			input.append(COMPLETE);
		else
			input.append(INCOMPLETE);
//		if (i.getAssignedUser().getUsername() == null)
//			;
//		else
//			input.append(",\'" + i.getAssignedUser().getUsername() + "\'");
		
		sql = "INSERT INTO Image (ProjectKey, FileString, IsComplete) " +
						"VALUES (" + input.toString() + ");";
		
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try
		{
			stmt.execute(sql);
			result = true;
			
			keyStmt = conn.createStatement();
			keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
			keyRS.next();
			int id = keyRS.getInt(1);
			i.setImageID(id);
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to insert User: " + i.toString());
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
