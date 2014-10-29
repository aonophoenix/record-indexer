/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.xml.XmlFileImporter;

/**
 * @author Justin
 *
 */
public class DatabaseAccessorTest
{
	private DatabaseAccessor databaseAccessor;
	private DatabaseAccessor databaseAccessor1;
	private DatabaseAccessor databaseAccessor2;
	private DatabaseAccessor databaseAccessor3;
	
	private XmlFileImporter xmlImporter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		System.out.println("## SET UP ##");
		databaseAccessor = new DatabaseAccessor();
		xmlImporter = null;
		try
		{
			xmlImporter = new XmlFileImporter("data/test.xml");
			xmlImporter.parseFile();
			xmlImporter.convert();
		}
		catch (FileNotFoundException e)
		{
			fail("did not import xml file");
		}
		
		
			
		Connection c = databaseAccessor.getDatabaseConnection().getCpds().getConnection();
		Statement stmt = c.createStatement();
		
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
		
		String dropImage = "DROP TABLE IF EXISTS Image;";
		String createImage = 
				"CREATE TABLE Image (" +
				"ImageKey INTEGER PRIMARY KEY AUTOINCREMENT," +
				"ProjectKey INTEGER," +
				"FileString TEXT NON NULL," +
				"IsComplete INTEGER," +
				"AssignedUser TEXT," +
				"FOREIGN KEY (ProjectKey) REFERENCES Project(ProjectKey)," +
				"FOREIGN KEY (AssignedUser) REFERENCES User(Username)" +
				");";
		
		stmt.executeUpdate(dropUser);
		stmt.executeUpdate(createUser);
		stmt.executeUpdate(dropProject);
		stmt.executeUpdate(createProject);
		stmt.executeUpdate(dropImage);
		stmt.executeUpdate(createImage);
		
		stmt.close();
		c.close();
		
		System.out.println("// SET UP //");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		System.out.println("## TEAR DOWN ##");
		
		System.out.println("// TEAR DOWN //");
	}

	/**
	 * Test method for {@link server.database.DatabaseAccessor#DatabaseAccessor()}.
	 * @throws SQLException 
	 */
//	@Test
//	public void testDatabaseAccessor() throws SQLException
//	{
//		try
//		{
//			databaseAccessor1 = new DatabaseAccessor("testfolder\\test1.sqlite");
//		}
//		catch (IOException e)
//		{
//			System.err.println("Could not initialize test1 database");
//		}
//		try
//		{
//			databaseAccessor2 = new DatabaseAccessor("testfolder\\test2.sqlite");
//		}
//		catch (IOException e)
//		{
//			System.err.println("Could not initialize test2 database");
//		}
//		
//		assertTrue(databaseAccessor1.getDatabaseConnection() != null);
//		Connection conn1 = databaseAccessor1.getDatabaseConnection().getCpds().getConnection();
//		Statement stmt1 = conn1.createStatement();
//		
//		String dropUser = "DROP TABLE User;";
//		String createUser = 
//				"CREATE TABLE User (" +
//				"UserKey INTEGER PRIMARY KEY AUTOINCREMENT," +
//				"Username TEXT NON NULL UNIQUE," +
//				"Password TEXT NON NULL," +
//				"FirstName TEXT NON NULL," +
//				"LastName TEXT NON NULL," +
//				"Email TEXT," +
//				"IndexedRecords INTEGER DEFAULT 0," +
//				"CurrentBatch INTEGER DEFAULT -1);";
//		
//		stmt1.executeUpdate(dropUser);
//		stmt1.executeUpdate(createUser);
//		stmt1.close();
//		conn1.close();
//		
//		assertTrue(databaseAccessor2.getDatabaseConnection() != null);
//		Connection conn2 = databaseAccessor2.getDatabaseConnection().getCpds().getConnection();
//		Statement stmt2 = conn2.createStatement();
//		stmt2.executeUpdate(dropUser);
//		stmt2.executeUpdate(createUser);
//		stmt2.close();
//		conn2.close();
//	}

	/**
	 * Test method for {@link server.database.DatabaseAccessor#storeXmlData(server.IndexerData)}.
	 * @throws SQLException 
	 */
	@Test
	public void testStoreXmlData() throws SQLException
	{
		
		boolean successful = databaseAccessor.storeXmlData(xmlImporter.getIndexerData());
		System.out.println("imported xml to database: " + successful);
		
		
		
		
	}

}
