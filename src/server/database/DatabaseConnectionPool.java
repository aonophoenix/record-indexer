/**
 * 
 */
package server.database;

import java.beans.PropertyVetoException;
import java.io.File;

import server.ServerException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Justin
 *
 */
public class DatabaseConnectionPool
{
	private final String DRIVER = "org.sqlite.JDBC";
	private final String databaseName = "data" + File.separator +
			"database" + File.separator +
			"default.sqlite";
	private String connectionURL;
	private ComboPooledDataSource cpds;
	
	private static DatabaseConnectionPool connectionPool;
	
	public static DatabaseConnectionPool getConnectionPool(String... arg)
	{
		if (connectionPool != null) //arguments ignored when connectionPool has already been created
			return connectionPool;
		
		if (arg.length == 0)
			connectionPool = new DatabaseConnectionPool();
		else if (arg.length == 1)
			connectionPool = new DatabaseConnectionPool(arg[0]);
		else
			return null; //don't give it more than one argument
		
		return connectionPool;
	}
	
	private DatabaseConnectionPool()
	{
		connectToDatabase("");
	}
	
	private DatabaseConnectionPool(String s)
	{
		connectToDatabase(s);
	}

	public boolean connectToDatabase(String s)
	{
		boolean result = false;
		cpds = new ComboPooledDataSource();
		String databaseTempName = null;
		
		try
		{
			cpds.setDriverClass(DRIVER);
		}
		catch (PropertyVetoException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		
		if (!s.isEmpty() && s.contains("sqlite"))
		{
			databaseTempName = "data\\database\\" + s;
			connectionURL = "jdbc:sqlite:" + databaseTempName;
		}
		else if (!s.isEmpty() && !s.contains("sqlite"))
			return false;
		else
			connectionURL = "jdbc:sqlite:" + databaseName;
		
		cpds.setJdbcUrl(connectionURL);
		
		result = true;
		
		return result;
	}

	public ComboPooledDataSource getCpds()
	{
		return cpds;
	}
	
}
