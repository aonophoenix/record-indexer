/**
 * 
 */
package server.database.accessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import server.ServerException;
import server.database.DatabaseAccessor;
import server.database.DatabaseConnectionPool;
import shared.communication.DownloadBatchField;
import shared.communication.DownloadBatch_Input;
import shared.communication.DownloadBatch_Output;
import shared.communication.GetFields_Output;
import shared.communication.GetProjects_Output;
import shared.communication.GetSampleImage_Input;
import shared.communication.GetSampleImage_Output;
import shared.communication.RecordValues;
import shared.communication.SubmitBatch_Output;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class ImageAccessor
{
//	private Connection conn;
//	private Statement stmt;
	private final int COMPLETE = 1;
	private final int INCOMPLETE = 0;
	
	
	
	public ImageAccessor(Connection c) throws SQLException
	{
//		this.conn = c;
//		stmt = conn.createStatement();
	}
	
	public ImageAccessor()
	{
		
	}
	
	public boolean addNewImage(Image i, int projectKey) throws ServerException
	{
		boolean result = false;
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				StringBuilder input = new StringBuilder();
				String sql = null;
				
				input.append(projectKey + ",");
				input.append("\'" + i.getFileString() + "\',");
				
				if (i.getIsComplete())
					input.append(COMPLETE);
				else
					input.append(INCOMPLETE);
				
				sql = "INSERT INTO Image (ProjectKey, FileString, IsComplete) " +
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
							i.setImageID(id);
						}
						
					}
					
				}
				
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to insert Image: " + i.toString());
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
		
//		if (i.getAssignedUser().getUsername() == null)
//			;
//		else
//			input.append(",\'" + i.getAssignedUser().getUsername() + "\'");
		
	}

	public GetSampleImage_Output getSampleImage(GetSampleImage_Input gsii) throws ServerException
	{
		GetSampleImage_Output gsio = null;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				int tempKey = gsii.getProjectID();
				String sql = "SELECT FileString FROM Image WHERE ProjectKey = " + tempKey + ";";
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
						gsio = new GetSampleImage_Output(rs.getString(1));
					else
						gsio = new GetSampleImage_Output(false, "");
					
				}
			}
		}
		
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete query");
			return null;
		}
		
		return gsio;
	}

	public DownloadBatch_Output getBatchWithPid(DownloadBatch_Input dbi) throws ServerException
	{
		DownloadBatch_Output dbo = null;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				int tempProjKey = dbi.getProjectID();
				int tempImgKey = -1;
				String sql1 = "SELECT ImageKey FROM Image WHERE ProjectKey = " + tempProjKey + " AND AssignedUser IS NULL;";
				
				try (ResultSet rs1 = stmt.executeQuery(sql1))
				{
					if (rs1.next())
						tempImgKey = rs1.getInt(1);
					else
						dbo = new DownloadBatch_Output(false, false);
				}
				
				if (tempImgKey > 0)
				{
					String sql2 = "SELECT " +
									 	"Project.FirstYCoord,Project.RecordHeight,Project.RecordsPerImage," +
									 	"Image.ImageKey,Image.FileString," +
									 	"Field.FieldKey,Field.ColNumber,Field.Title,Field.HelpHtml," +
									 	"Field.XCoord,Field.Width,Field.KnownData" +
								 "  FROM Project,Image,Field" +
								 " WHERE Project.ProjectKey = " + tempProjKey +
								 "   AND Project.ProjectKey = Image.ProjectKey" +
								 "   AND Image.ProjectKey = Field.ProjectKey" +
								 "   AND Image.AssignedUser IS NULL" +
								 "   AND Image.ImageKey = " + tempImgKey +
							  " ORDER BY Field.ColNumber;";
					try (ResultSet rs2 = stmt.executeQuery(sql2);)
					{
						while (rs2.next())
						{
							if (dbo == null)
								dbo = new DownloadBatch_Output(true, rs2.getInt(4), tempProjKey, rs2.getString(5),
															rs2.getInt(1), rs2.getInt(2), rs2.getInt(3));
							
							DownloadBatchField temp = new DownloadBatchField(rs2.getInt(6), rs2.getInt(7), rs2.getString(8),
																	rs2.getString(9), rs2.getInt(10), rs2.getInt(11),
																	rs2.getString(12));
							dbo.addField(temp);
							
						}
					}
				//####################################
				}
			}
		}
		
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete query");
			return null;
		}
		
		return dbo;
		
/**
  SELECT Project.FirstYCoord,Project.RecordHeight,Project.RecordsPerImage,
         Image.ImageKey,Image.FileString,
         Field.FieldKey,Field.ColNumber,Field.Title,Field.HelpHtml,
         Field.XCoord,Field.Width,Field.KnownData
    FROM Project,Image,Field
   WHERE Project.ProjectKey = 2
     AND Project.ProjectKey = Image.ProjectKey
     AND Image.ProjectKey = Field.ProjectKey
     AND Image.AssignedUser IS NULL
     AND Image.ImageKey = 21
ORDER BY Field.ColNumber;
 */
	}
	
	public SubmitBatch_Output submitBatch(int batchID, List<RecordValues> recordValues)
	{
		SubmitBatch_Output sbo = null;
		
		PreparedStatement submitStatement = null
		
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			String submitString = "INSERT INTO Record ()";
			
			
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				
				
				
				
/**
SELECT Value.FieldKey,Record.RowNum,Value.Name,Value.Value FROM Value
 INNER JOIN Record
  ON Record.RecordKey=Value.RecordKey
 WHERE Record.ImageKey=2;
 */

			}
		}
		
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete operation");
			return null;
		}
		
		
		
		return sbo;
	}
	
	private int getRecordsPerImage(int batchID)
	{
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				
			}
		}
		
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete operation");
			return null;
		}

		
		
		
		
	}
	
	
	

}
