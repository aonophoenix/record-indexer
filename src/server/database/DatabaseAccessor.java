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
import server.database.accessor.*;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class DatabaseAccessor
{
	protected final DatabaseConnectionPool databaseConnectionPool;
	
	public DatabaseAccessor()
	{
		databaseConnectionPool = new DatabaseConnectionPool();
		databaseConnectionPool.connectToDatabase("");
	}
	
	public DatabaseAccessor(String f) throws IOException
	{
		databaseConnectionPool = new DatabaseConnectionPool();
		databaseConnectionPool.connectToDatabase(f);
	}
	
	public boolean storeXmlData(IndexerData idex)
	{
		clearTables();
		
		User[] userArray = idex.getUsers().toArray(new User[0]);
		Project[] projectArray = idex.getProjects().toArray(new Project[0]);
		UserAccessor ua = null;
		ProjectAccessor pa = null;
		ImageAccessor ia = null;
		FieldAccessor fa = null;
		RecordAccessor ra = null;
		ValueAccessor va = null;
		
		boolean result = true;
		int imageError = -1;
		int fieldError = -1;
		int recordError = -1;
		int valueError = -1;
		
		ua = new UserAccessor();
		pa = new ProjectAccessor();
		ia = new ImageAccessor();
		fa = new FieldAccessor();
		ra = new RecordAccessor();
		va = new ValueAccessor();
		
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
			if (projectArray[i].getFields() != null)
			{
				fieldArray = projectArray[i].getFields().toArray(new Field[0]);

				for (int j = 0; j < fieldArray.length; j++)
				{
					if (!fa.addNewField(fieldArray[j], j,
							projectArray[i].getProjectKey())
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
							System.out.println(ra.addNewRecord(recordArray[k],
									imageArray[j].getImageID())
									& result)
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
							if (recordArray[k].getValues().getValues() != null)
								valueArray = recordArray[k].getValues()
										.getValues().toArray(new String[0]);
							
//							System.out.println("Length of valueArray: " + valueArray.length);
							for (int l = 0; l < valueArray.length; l++)
							{
								
								System.out.println(va.addNewValue(fieldArray[l].getTitle(),
										valueArray[l],
										recordArray[k].getRecordKey())
										& result)
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
		Connection conn = null;
		Statement stmt = null;
		boolean result = true;
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
				"RecordHeight INTEGER NON NULL);";
		
		String dropField = "DROP TABLE IF EXISTS Field;";
		String createField = 
				"CREATE TABLE Field (" + 
				"FieldKey INTEGER PRIMARY KEY AUTOINCREMENT," +
				"ProjectKey INTEGER," +
				"RowNumber INTEGER NON NULL," +
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
				"RecordKey INTEGER PRIMARY KEY AUTOINCREMENT," +
				"ImageKey INTEGER," +
				"FOREIGN KEY (ImageKey) REFERENCES Image(ImageKey));";
		
		String dropValue = "DROP TABLE IF EXISTS Value;";
		String createValue = 
				"CREATE TABLE Value (" +
				"RecordKey INTEGER," +
				"Name TEXT NOT NULL," +
				"Value TEXT NOT NULL," +
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
		}
		
		try
		{
			stmt.close();
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to close statement: " + stmt);
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

	public DatabaseConnectionPool getDatabaseConnection()
	{
		return databaseConnectionPool;
	}
	
}
