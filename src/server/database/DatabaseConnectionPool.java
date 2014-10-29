/**
 * 
 */
package server.database;

import java.beans.PropertyVetoException;
import java.io.File;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Justin
 *
 */
public class DatabaseConnectionPool
{
	private final String DRIVER = "org.sqlite.JDBC";
	private String databaseName = "data" + File.separator +
			"database" + File.separator +
			"test.sqlite";
	private String connectionURL;
	private ComboPooledDataSource cpds;
	
	
	public DatabaseConnectionPool()
	{
		//initialize("");
	}

	public boolean connectToDatabase(String s)
	{
		boolean result = false;
		if (s.isEmpty())
		{
			initialize("");
			return true;
		}
		else if (!s.contains("sqlite"))
			return false;
		try
		{
			initialize(s);
			result = true;
		}
		catch (Exception e)
		{
			return false;
		}

		return result;
	}

	private void initialize(String s)
	{
		cpds = new ComboPooledDataSource();
		
		if (!s.isEmpty())
			this.databaseName = "data\\database\\" + s;
		
		try
		{
			cpds.setDriverClass(DRIVER);
		}
		catch (PropertyVetoException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		connectionURL = "jdbc:sqlite:" + databaseName;
		cpds.setJdbcUrl(connectionURL);
	}


	public ComboPooledDataSource getCpds()
	{
		return cpds;
	}
	
	
	
	
}
