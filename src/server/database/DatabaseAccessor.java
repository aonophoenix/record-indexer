/**
 * 
 */
package server.database;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import server.IndexerData;
import server.ServerException;
import server.database.accessor.*;
import shared.communication.DownloadBatch_Input;
import shared.communication.DownloadBatch_Output;
import shared.communication.GetFields_Input;
import shared.communication.GetFields_Output;
import shared.communication.GetProjects_Input;
import shared.communication.GetProjects_Output;
import shared.communication.GetSampleImage_Input;
import shared.communication.GetSampleImage_Output;
import shared.communication.Search_Input;
import shared.communication.Search_Output;
import shared.communication.SubmitBatch_Input;
import shared.communication.SubmitBatch_Output;
import shared.communication.ValidateUser_Input;
import shared.communication.ValidateUser_Output;
import shared.communication.Validity;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class DatabaseAccessor
{
	private DatabaseConnectionPool connectionPool;
	private UserAccessor ua;
	private ProjectAccessor pa;
	private ImageAccessor ia;
	private FieldAccessor fa;
	private RecordAccessor ra;
	private ValueAccessor va;
	
	private final boolean VALID_USER = true;
	private final boolean INVALID_USER = false;
	private final boolean OPERATION_SUCCESSFUL = true;
	private final boolean OPERATION_UNSUCCESSFUL = false;
	private final boolean BATCH_ASSIGNED = true;
	private final boolean BATCHES_AVAILABLE = true;
	
//	private static DatabaseAccessor singleton;
//	
//	public static DatabaseAccessor getSingleton(String... arg) throws ServerException
//	{
//		if (singleton != null)
//			return singleton;
//		
//		if (arg.length == 0)
//			singleton = new DatabaseAccessor();
//		else if (arg.length == 1)
//			singleton = new DatabaseAccessor(arg[0]);
//		else
//			throw new ServerException();
//		
//		return singleton;
//	}
	
	public DatabaseAccessor()
	{
		connectionPool = DatabaseConnectionPool.getConnectionPool();
		
		ua = new UserAccessor();
		pa = new ProjectAccessor();
		ia = new ImageAccessor();
		fa = new FieldAccessor();
		ra = new RecordAccessor();
		va = new ValueAccessor();
	}
	
	public DatabaseAccessor(String f)
	{
		connectionPool = DatabaseConnectionPool.getConnectionPool(f);
		
		ua = new UserAccessor();
		pa = new ProjectAccessor();
		ia = new ImageAccessor();
		fa = new FieldAccessor();
		ra = new RecordAccessor();
		va = new ValueAccessor();
	}
	
	public boolean storeXmlData(IndexerData idex) throws ServerException
	{
		clearTables();
		
		User[] userArray = idex.getUsers().toArray(new User[0]);
		Project[] projectArray = idex.getProjects().toArray(new Project[0]);
		
		
		
		boolean result = true;
		int imageError = -1;
		int fieldError = -1;
		int recordError = -1;
		int valueError = -1;
		
		
		
		for (int i = 0; i < userArray.length; i++)
		{
			
			if (!ua.addNewUser(userArray[i]))
				result = false;
		}
		for (int i = 0; i < projectArray.length; i++)
		{
			
			if (!pa.addNewProject(projectArray[i]))
				result = false;
			
			Field[] fieldArray = null;
			int[] fieldKeys = null;
//			System.out.println("new field keys array");
			
			if (projectArray[i].getFields() != null)
			{
				fieldArray = projectArray[i].getFields().toArray(new Field[0]);
				fieldKeys = new int[projectArray[i].getFields().size()];
				
				
				for (int j = 0; j < fieldArray.length; j++)
				{
					if (!fa.addNewField(fieldArray[j], j + 1,
							projectArray[i].getProjectKey(), fieldKeys)
							& result)
					{
						result = false;
						fieldError = j;
					}
				}
			}
			
			Image[] imageArray = null;
			if (projectArray[i].getImages() != null)
			{
				imageArray = projectArray[i].getImages().toArray(new Image[0]);
				
				for (int j = 0; j < imageArray.length; j++)
				{
					if (!ia.addNewImage(imageArray[j],
							projectArray[i].getProjectKey())
							& result)
					{
						result = false;
						imageError = j;
					}
					Record[] recordArray = null;
					if (imageArray[j].getRecords() != null)
					{
						recordArray = imageArray[j].getRecords().toArray(
								new Record[0]);
						
						for (int k = 0; k < recordArray.length; k++)
						{
//							System.out.println(
									ra.addNewRecord(k + 1, recordArray[k],
									imageArray[j].getImageID())
//									& result)
								;
//							{
//								System.out.println("Index: " + k);
//								System.out.println("Record: "
//										+ recordArray[k]);
//								System.out.println("ImageKey: " + imageArray[j].getImageID());
//								
//								result = false;
//								recordError = j;
//							}

							String[] valueArray = null;
						//	System.out.println(fieldKeys[0] + " " + fieldKeys[1] + " " + fieldKeys[2] + " " + fieldKeys[3]);
							
							if (recordArray[k].getValues().getValues() != null)
								valueArray = recordArray[k].getValues()
										.getValues().toArray(new String[0]);
							
//							System.out.println("Length of valueArray: " + valueArray.length);
							for (int l = 0; l < valueArray.length; l++)
							{
								
//								System.out.println(fieldKeys[l]);
//								System.out.println(
										va.addNewValue(fieldArray[l].getTitle(),
										valueArray[l],
										recordArray[k].getRecordKey(), fieldKeys[l])
//										& result)
									;
//								{
//								System.out.println("Index: " + l);
//								System.out.println("Name: "
//										+ fieldArray[l].getTitle());
//								System.out.println("Value: " + valueArray[l]);
//									
//									result = false;
//									valueError = j;
//								}
							}
						}
					}

				}

			}
			
	//		System.out.println("######\n" + projectArray[i].toString() + "######\n" + imageError + fieldError + recordError + valueError + "######\n");
			
		}
		
		
		
		return result;
	}

	private boolean clearTables()
	{
		boolean result = true;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				String dropUser = "DROP TABLE IF EXISTS User;";
				String createUser = 
						"CREATE TABLE User (" +
						"UserKey INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Username TEXT NON NULL UNIQUE," +
						"Password TEXT NON NULL," +
						"FirstName TEXT NON NULL," +
						"LastName TEXT NON NULL," +
						"Email TEXT," +
						"IndexedRecords INTEGER DEFAULT 0," +
						"CurrentBatch INTEGER DEFAULT -1);";
				
				String dropProject = "DROP TABLE IF EXISTS Project;";
				String createProject = 
						"CREATE TABLE Project (" +
						"ProjectKey INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Title TEXT NON NULL," +
						"RecordsPerImage INTEGER NON NULL," +
						"FirstYCoord INTEGER NON NULL," +
						"RecordHeight INTEGER NON NULL," +
						"NumberOfFields INTEGER NON NULL);";
				
				String dropField = "DROP TABLE IF EXISTS Field;";
				String createField = 
						"CREATE TABLE Field (" + 
						"FieldKey INTEGER PRIMARY KEY AUTOINCREMENT," +
						"ProjectKey INTEGER," +
						"ColNumber INTEGER NON NULL," +
						"Title TEXT NON NULL," +
						"XCoord INTEGER NON NULL," +
						"Width INTEGER NON NULL," +
						"HelpHtml TEXT," +
						"KnownData TEXT," +
						"FOREIGN KEY (ProjectKey) REFERENCES Project(ProjectKey));";
				
				String dropImage = "DROP TABLE IF EXISTS Image;";
				String createImage = 
						"CREATE TABLE Image (" +
						"ImageKey INTEGER PRIMARY KEY AUTOINCREMENT," +
						"ProjectKey INTEGER," +
						"FileString TEXT NON NULL," +
						"IsComplete INTEGER," +
						"AssignedUser TEXT," +
						"FOREIGN KEY (ProjectKey) REFERENCES Project(ProjectKey)," +
						"FOREIGN KEY (AssignedUser) REFERENCES User(Username));";
				
				String dropRecord = "DROP TABLE IF EXISTS Record;";
				String createRecord = 
						"CREATE TABLE Record (" +
						"RowNum INTEGER," +
						"RecordKey INTEGER PRIMARY KEY AUTOINCREMENT," +
						"ImageKey INTEGER," +
						"FOREIGN KEY (ImageKey) REFERENCES Image(ImageKey));";
				
				String dropValue = "DROP TABLE IF EXISTS Value;";
				String createValue = 
						"CREATE TABLE Value (" +
						"FieldKey INTEGER," +
						"RecordKey INTEGER," +
						"Name TEXT NOT NULL," +
						"Value TEXT NOT NULL," +
						"FOREIGN KEY (FieldKey) REFERENCES Field(FieldKey)," +
						"FOREIGN KEY (RecordKey) REFERENCES Record(RecordKey));";
				
				
				int errorLevel = -1;
				try
				{
					errorLevel++;
					stmt.executeUpdate(dropUser);		//errorLevel == 0 if this fails
					errorLevel++;
					stmt.executeUpdate(createUser);		//errorLevel == 1 if this fails
					errorLevel++;
					stmt.executeUpdate(dropProject);	//errorLevel == 2 if this fails
					errorLevel++;
					stmt.executeUpdate(createProject);	//errorLevel == 3 if this fails
					errorLevel++;
					stmt.executeUpdate(dropField);		//errorLevel == 4 if this fails
					errorLevel++;
					stmt.executeUpdate(createField);	//errorLevel == 5 if this fails
					errorLevel++;
					stmt.executeUpdate(dropImage);		//errorLevel == 6 if this fails
					errorLevel++;
					stmt.executeUpdate(createImage);	//errorLevel == 7 if this fails
					errorLevel++;
					stmt.executeUpdate(dropRecord);		//errorLevel == 8 if this fails
					errorLevel++;
					stmt.executeUpdate(createRecord);	//errorLevel == 9 if this fails
					errorLevel++;
					stmt.executeUpdate(dropValue);		//errorLevel == 10 if this fails
					errorLevel++;
					stmt.executeUpdate(createValue);	//errorLevel == 11 if this fails
				}
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to update table; errorLevel: " + errorLevel);
					return false;
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

	public ValidateUser_Output validateUser(ValidateUser_Input vui) throws ServerException
	{
		ValidateUser_Output vuo = null;
		vuo = ua.validateUser(vui);
		
		if (vuo == null)
			throw new ServerException();
		
		return vuo;
	}
	
	public GetProjects_Output getProjects(GetProjects_Input gpi) throws ServerException
	{
		ValidateUser_Output vuo = ua.quickValidation(gpi.getUsername(), gpi.getPassword());
		GetProjects_Output gpo = null;
		
		if(vuo.isValid())
			gpo = pa.getProjects(gpi);
		else
			gpo = new GetProjects_Output(INVALID_USER);
		
		return gpo;
	}
	
	public GetSampleImage_Output getSampleImage(GetSampleImage_Input gsii) throws ServerException
	{
		ValidateUser_Output vuo = ua.quickValidation(gsii.getUsername(), gsii.getPassword());
		GetSampleImage_Output gsio = null;
		
		if(vuo.isValid())
			gsio = ia.getSampleImage(gsii);
		else
			gsio = new GetSampleImage_Output(INVALID_USER);	//invalid user
		
		return gsio;
	}
	
	public DownloadBatch_Output downloadBatch(DownloadBatch_Input dbi) throws ServerException
	{
		ValidateUser_Output vuo = ua.quickValidation(dbi.getUsername(), dbi.getPassword());
		DownloadBatch_Output dbo = null;
		
		if(vuo.isValid())
		{
			if (ua.hasBatchAssigned(dbi.getUsername()))
				dbo = new DownloadBatch_Output(BATCH_ASSIGNED, BATCHES_AVAILABLE);
			else
			{
				dbo = ia.getBatchWithPid(dbi);
				
				System.out.println("####" + dbo.getResult());
				
				if (dbo.getResult())
					ua.assignBatch(dbi.getUsername(), dbo.getBatchID());
			}
			
		}
		else
			dbo = new DownloadBatch_Output(INVALID_USER);	//invalid user
		
		
		return dbo;
	}
	
	public Search_Output search(Search_Input si) throws ServerException
	{
		ValidateUser_Output vuo = ua.quickValidation(si.getUsername(), si.getPassword());
		Search_Output so = null;
		
		if(vuo.isValid())			//if the user is valid
		{
			so = va.search(si.getFields(), si.getValues());
			
		}
		else
			so = new Search_Output(INVALID_USER);			//invalid user
		
		return so;
	}
	
	public GetFields_Output getFields(GetFields_Input gfi) throws ServerException
	{
		ValidateUser_Output vuo = ua.quickValidation(gfi.getUsername(), gfi.getPassword());
		GetFields_Output gfo = null;
		
		if(vuo.isValid())			//if the user is valid
		{
			gfo = fa.getFields(gfi.getProjectID());
			
		}
		else
			gfo = new GetFields_Output(INVALID_USER);			//invalid user
		
		
		
		return gfo;
	}
	
	public SubmitBatch_Output submitBatch(SubmitBatch_Input sbi)
	{
		ValidateUser_Output vuo = ua.quickValidation(sbi.getUsername(), sbi.getPassword());
		SubmitBatch_Output sbo = null;
		
		if(vuo.isValid())			//if the user is valid
		{
			sbo = ia.submitBatch(sbi.getBatchID(), sbi.getRecordValues());
			
		}
		else
			sbo = new SubmitBatch_Output(INVALID_USER, Validity.UNKNOWN, OPERATION_UNSUCCESSFUL);
		
		return sbo;
	}

	public UserAccessor getUa()
	{
		return ua;
	}

	public ProjectAccessor getPa()
	{
		return pa;
	}

	public ImageAccessor getIa()
	{
		return ia;
	}

	public FieldAccessor getFa()
	{
		return fa;
	}

	public RecordAccessor getRa()
	{
		return ra;
	}

	public ValueAccessor getVa()
	{
		return va;
	}

	public DatabaseConnectionPool getConnectionPool()
	{
		return connectionPool;
	}

//UPDATE image SET iscomplete=1 where projectkey=1;
}
