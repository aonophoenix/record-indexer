package server.database;

import java.io.IOException;
import java.sql.*;

public class Updater
{
	private Connection c;
	private String databaseString;
	
	
	public Updater()
	{
		initialize("");
	}
	
	public Updater(String f) throws IOException
	{
		if (!f.contains("sqlite"))
			throw new IOException();
		initialize(f);
	}

	
	private void initialize(String s)
	{
		Connection c = null;
		if (s.isEmpty())
			this.databaseString = "jdbc:sqlite:data\\database\\test.sqlite";
		else
			this.databaseString = "jdbc:sqlite:data\\database\\" + s;
		try
		{
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		
		
		try
		{
			c = DriverManager.getConnection(this.databaseString);
			
			
		}
		catch (SQLException e)
		{
			System.err.println("Failed to connect to database");
			
		}
	}
	
	public int insert(String s)
	{
		int result = 0;
		
		
		
			try
			{
				c.close();
			}
			catch (SQLException e)
			{
				System.err.println("Failed to close SQL Connection");
			}
		
		
		return result;
	}
	
	public int drop(String s)
	{
		int result = 0;
		
		
		return result;
	}
	
	public void store()
	{
		
	}
	
	public void restore()
	{
		
	}
	
	
	
	
	
}
