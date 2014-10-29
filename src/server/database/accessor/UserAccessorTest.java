/**
 * 
 */
package server.database.accessor;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import shared.model.User;

/**
 * @author Justin
 *
 */
public class UserAccessorTest
{
	private Connection c;
	private String databaseString;
	private Statement stmt;
	private UserAccessor ua;
	
	protected static ComboPooledDataSource cpds;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		System.out.println("## SET UP ##");
		this.databaseString = "jdbc:sqlite:data\\database\\test.sqlite";
		
		cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl( databaseString );
		
		cpds.setDriverClass( "org.sqlite.JDBC" );
		
		try
		{
			
			c = cpds.getConnection();
			
		}
		catch (SQLException e)
		{
			System.err.println("Failed to connect to database");
		}
		
		stmt = c.createStatement();
		
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
		
		stmt.executeUpdate(dropUser);
		stmt.executeUpdate(createUser);
		stmt.close();
		c.close();
		
		ua = new UserAccessor();
		System.out.println("// SET UP //");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		System.out.println("## TEAR DOWN ##");
		//System.out.println("Statement state: " + c.isClosed());
		
		//c.close();
		System.out.println("// TEAR DOWN //");
	}

	@Test
	public void testAddNewUser() throws SQLException
	{
		//checkSelect();
		
		User user1 = new User("username1", "password1", "firstname1", "lastname1", "email1", 0);
		System.out.println("User added: " + ua.addNewUser(user1));
		
		//rs = stmt.executeQuery(select1);
	//	checkSelect();
		
		User user2 = new User("username2", "password2", "firstname2", "lastname2", "email2", 0);
		System.out.println("User added: " + ua.addNewUser(user2));
		//rs = stmt.executeQuery(select1);
	//	checkSelect();
		
	}
	
	private void checkSelect() throws SQLException
	{
		String select1 = "SELECT * FROM User;";
		ResultSet rs = null;
		
		
		rs = stmt.executeQuery(select1);
		while(rs.next())
		{
		int id = rs.getInt(1);
		String username = rs.getString(2);
		String password = rs.getString(3);
		String firstname = rs.getString(4);
		String lastname = rs.getString(5);
		String email = rs.getString(6);
		int indexedrecords = rs.getInt(7);
		int batchassigned = rs.getInt(8);
		
		System.out.println("***********");
		System.out.println(id);
		System.out.println(username);
		System.out.println(password);
		System.out.println(firstname);
		System.out.println(lastname);
		System.out.println(email);
		System.out.println(indexedrecords);
		System.out.println(batchassigned);
		System.out.println("***********");
		
		}
		
		
	}
	
	

}
