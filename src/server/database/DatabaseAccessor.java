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
		boolean result = true;
		int imageError = -1;
		
		ua = new UserAccessor();
		pa = new ProjectAccessor();
		ia = new ImageAccessor();
		
		for (int i = 0; i < userArray.length; i++)
		{
			
			if (!ua.addNewUser(userArray[i]))
				result = false;
		}
		for (int i = 0; i < projectArray.length; i++)
		{
			
			if (!pa.addNewProject(projectArray[i]))
				result = false;
			
			Image[] imageArray = projectArray[i].getImages().toArray(new Image[0]);
			
			for (int j = 0; j < imageArray.length; j++)
			{
				if (!ia.addNewImage(imageArray[j], projectArray[i].getProjectKey()) && result)
				{
					result = false;
					imageError = j;
				}
			}
			
			System.out.println("######\n" + projectArray[i].toString() + "######\n" + imageError + "######\n");
			
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
						"Order INTEGER NON NULL," +
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
				"FirstName TEXT," +
				"LastName TEXT," +
				"Age INTEGER," +
				"Ethnicity TEXT," +
				"Gender TEXT," +
				"FOREIGN KEY (ImageKey) REFERENCES Image(ImageKey));";
		
		int errorLevel = -1;
		try
		{
			errorLevel++;
			stmt.executeUpdate(dropUser);
			errorLevel++;
			stmt.executeUpdate(createUser);
			errorLevel++;
			stmt.executeUpdate(dropProject);
			errorLevel++;
			stmt.executeUpdate(createProject);
			errorLevel++;
			stmt.executeUpdate(dropField);
			errorLevel++;
			stmt.executeUpdate(createField);
			errorLevel++;
			stmt.executeUpdate(dropImage);
			errorLevel++;
			stmt.executeUpdate(createImage);
			errorLevel++;
			stmt.executeUpdate(dropRecord);
			errorLevel++;
			stmt.executeUpdate(createRecord);
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
