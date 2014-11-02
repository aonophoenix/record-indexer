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
public class FieldAccessor extends DatabaseAccessor
{
	private Connection conn;
	private Statement stmt;
	
	public FieldAccessor(Connection c) throws SQLException
	{
		this.conn = c;
		stmt = conn.createStatement();
	}
	
	public FieldAccessor()
	{
		
	}
	
	public boolean addNewField(Field f, int row, int projectKey)
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
		
		input.append(projectKey + ",");
		input.append(row + ",");
		input.append("\'" + f.getTitle() + "\',");
		input.append(f.getxCoord() + ",");
		input.append(f.getWidth() + ",\'");
		input.append(f.getHelpHtml() + "\',\'");
		input.append(f.getKnownData() + "\'");
		
		sql = "INSERT INTO Field (ProjectKey, RowNumber, Title, XCoord, Width, HelpHtml, KnownData) " +
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
			f.setFieldKey(id);
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to insert Field: " + f.toString());
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
