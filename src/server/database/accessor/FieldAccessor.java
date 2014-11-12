/**
 * 
 */
package server.database.accessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.ServerException;
import server.database.DatabaseAccessor;
import server.database.DatabaseConnectionPool;
import shared.communication.GetFields_Output;
import shared.communication.GetSampleImage_Output;
import shared.communication.Validity;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class FieldAccessor
{
	private final boolean VALID_USER = true;
	private final boolean INVALID_USER = false;
//	private Connection conn;
//	private Statement stmt;
	
	public FieldAccessor(Connection c) throws SQLException
	{
//		this.conn = c;
//		stmt = conn.createStatement();
	}
	
	public FieldAccessor()
	{
		
	}
	
	public boolean addNewField(Field f, int col, int projectKey, int[] keys) throws ServerException
	{
		boolean result = false;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				StringBuilder input = new StringBuilder();
				String sql = null;
				
				input.append(projectKey + ",");
				input.append(col + ",");
				input.append("\'" + f.getTitle() + "\',");
				input.append(f.getxCoord() + ",");
				input.append(f.getWidth() + ",\'");
				input.append(f.getHelpHtml() + "\',\'");
				input.append(f.getKnownData() + "\'");
				
				sql = "INSERT INTO Field (ProjectKey, ColNumber, Title, XCoord, Width, HelpHtml, KnownData) " +
						"VALUES (" + input.toString() + ");";
				
				try
				{
					stmt.execute(sql);
					result = true;
					
					try (ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()"))
					{
						if (rs.next())
						{
							int id = rs.getInt(1);
							f.setFieldKey(id);
							keys[col - 1] = id;
						}
					}
				}
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to insert Field: " + f.toString());
				}
				
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete update");
			return false;
		}
		
		return result;
	}

	public GetFields_Output getFields(int pid)
	{
		GetFields_Output gfo = null;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				String sql = null;
				int tempKey = pid;
				if (pid == -1)
					sql = "SELECT ProjectKey,FieldKey,Title"
						+ " FROM Field;";
				else
					sql = "SELECT ProjectKey,FieldKey,Title"
						+ " FROM Field"
						+ " WHERE ProjectKey=" + pid + ";";
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
					{
						gfo = new GetFields_Output();
						gfo.addField(rs.getInt(1), rs.getInt(2), rs.getString(3));
						
						while (rs.next())
						{
							gfo.addField(rs.getInt(1), rs.getInt(2), rs.getString(3));
						}
					}
					else
						gfo = new GetFields_Output(VALID_USER, Validity.INVALID);
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getMessage());
			System.err.println("Unable to complete query");
			return null;
		}
		
		return gfo;
	}
}
