package server.database;

import java.io.IOException;
import java.sql.*;

public class Fetcher
{
	private Connection c;
	private String database;
	
	public Fetcher()
	{
		initialize("");
	}
	
	public Fetcher(String f) throws IOException
	{
		if (!f.contains("sqlite"))
			throw new IOException();
		initialize(f);
	}
	
	private void initialize(String s)
	{
		if (s.isEmpty())
			this.database = "jdbc:sqlite:database\\test.sqlite";
		else
			this.database = "jdbc:sqlite:database\\" + s;
		try
		{
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public int select()
	{
		int result = 0;
		
		
		
		
		
		return result;
	}
	
	
}
