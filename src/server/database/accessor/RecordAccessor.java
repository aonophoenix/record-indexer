/**
 * 
 */
package server.database.accessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.ServerException;
import server.database.DatabaseConnectionPool;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class RecordAccessor
{
//	private Connection conn;
//	private Statement stmt;
	
	public RecordAccessor(Connection c) throws SQLException
	{
//		this.conn = c;
//		stmt = conn.createStatement();
	}
	
	public RecordAccessor()
	{
		
	}
	
	public boolean addNewRecord(int row, Record r, int imageKey) throws ServerException
	{
		boolean result = false;
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				StringBuilder input = new StringBuilder();
				String sql = null;
				
				input.append(row + ",");
				input.append(imageKey);
				
				sql = "INSERT INTO Record (RowNum, ImageKey) " +
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
							r.setRecordKey(id);
						}
					}
				}
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to insert Record: " + r.toString());
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
}