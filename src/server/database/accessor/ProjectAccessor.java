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
import shared.communication.GetProjects_Input;
import shared.communication.GetProjects_Output;
import shared.communication.ValidateUser_Output;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class ProjectAccessor
{
//	private Connection conn;
//	private Statement stmt;
	
	
	public ProjectAccessor(Connection c) throws SQLException
	{
//		this.conn = c;
//		stmt = conn.createStatement();
	}
	
	public ProjectAccessor()
	{
		
	}
	
	public boolean addNewProject(Project p) throws ServerException
	{
		boolean result = false;
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				StringBuilder input = new StringBuilder();
				
				input.append("\'" + p.getTitle() + "\',\'");
				input.append(p.getRecordsPerImage() + "\',\'");
				input.append(p.getFirstYCoord() + "\',\'");
				input.append(p.getRecordHeight() + "\',");
				input.append(p.getFields().size());
				
				String sql = "INSERT INTO Project (Title, RecordsPerImage, FirstYCoord, RecordHeight, NumberOfFields) " +
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
							p.setProjectKey(id);
						}
					}
				}
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to insert Project: " + p.toString());
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

	public GetProjects_Output getProjects(GetProjects_Input gpi) throws ServerException
	{
		GetProjects_Output gpo = null;
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				String sql = "SELECT ProjectKey,Title FROM Project;";
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					gpo = new GetProjects_Output();
					
					while (rs.next())
						gpo.addProject(rs.getInt(1), rs.getString(2));
				}
			}
		}
		
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete query");
		}
		
		return gpo;
	}

}
