/**
 * 
 */
package server.database.accessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.ServerException;
import server.database.DatabaseAccessor;
import server.database.DatabaseConnectionPool;
import shared.model.*;
import shared.communication.Search_Output;

/**
 * @author Justin
 *
 */
public class ValueAccessor
{
//	private Connection conn;
//	private Statement stmt;
	
	private final boolean VALID_USER = true;
	private final boolean INVALID_USER = false;
	private final boolean MATCH_FOUND = true;
	private final boolean MATCH_NOT_FOUND = false;
	
	public ValueAccessor(Connection c) throws SQLException
	{
//		this.conn = c;
//		stmt = conn.createStatement();
	}
	
	public ValueAccessor()
	{
		
	}
	
	public boolean addNewValue(String n, String v, int recordKey, int fieldKey) throws ServerException
	{
		boolean result = false;
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				//####################################
				
				StringBuilder input = new StringBuilder();
				String sql = null;
				
				input.append(fieldKey + ",");
				input.append(recordKey + ",\'");
				input.append(n + "\',\'");
				input.append(v + "\'");
				
				sql = "INSERT INTO Value (FieldKey, RecordKey, Name, Value) " +
						"VALUES (" + input.toString() + ");";
				try
				{
					stmt.execute(sql);
					result = true;
				}
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to insert Name-Value Pair: " + n + "-" + v);
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

	public Search_Output search(Integer[] fields, String[] values)
	{
		Search_Output so = null;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				StringBuilder sb = new StringBuilder();
				String sql = null;
				
				StringBuilder fieldStringBuilder = new StringBuilder();
				StringBuilder valueStringBuilder = new StringBuilder();
				
				for (int i = 0; i < fields.length; i++)
				{
					if (i != 0)
					{
						fieldStringBuilder.append(",");
						valueStringBuilder.append(",");
					}
					fieldStringBuilder.append(fields[i]);
					valueStringBuilder.append("\'" + values[i] + "\'");
				}
				
				sb.append("SELECT Image.ImageKey,Image.FileString,Record.RowNum,Field.FieldKey ");
				sb.append("FROM Value ");
				sb.append("JOIN Record ");
				sb.append(	"ON Record.RecordKey=Value.RecordKey ");
				sb.append("JOIN Image ");
				sb.append(	"ON Image.ImageKey=Record.ImageKey ");
				sb.append("JOIN Field ");
				sb.append(	"ON Field.FieldKey=Value.FieldKey ");
				sb.append("WHERE Value.FieldKey IN (");
				sb.append(fieldStringBuilder.toString());
				sb.append(") AND Value.Value IN (");
				sb.append(valueStringBuilder.toString());
				sb.append(");");
				
				sql = sb.toString();
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
					{
						so = new Search_Output();
						so.addMatch(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
						while (rs.next())
						{
							so.addMatch(rs.getInt(1), rs.getString(2),
									rs.getInt(3), rs.getInt(4));
						}
					}
					if (so == null)
						so = new Search_Output(VALID_USER, MATCH_NOT_FOUND);
				}
			}
		
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete search query");
			return null;
		}
		
		return so;
	}
}
