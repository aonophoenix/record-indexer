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
import shared.communication.*;
import shared.model.*;

/**
 * @author Justin
 *
 */
public class UserAccessor
{
//	private Connection conn;
//	private Statement stmt;
	
	
	public UserAccessor(Connection c) throws SQLException
	{
//		this.conn = c;
//		stmt = conn.createStatement();
	}
	
	public UserAccessor()
	{
		
	}
	
	
	public boolean addNewUser(User u) throws ServerException
	{
		boolean result = false;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				StringBuilder input = new StringBuilder();
				
				input.append("\'" + u.getUsername() + "\',\'");
				input.append(u.getPassword() + "\',\'");
				input.append(u.getFirstname() + "\',\'");
				input.append(u.getLastname() + "\',\'");
				input.append(u.getEmail() + "\'");
				
				String sql = "INSERT INTO User (Username, Password, FirstName, LastName, Email) " +
						"VALUES (" + input.toString() + ");";
				
				try
				{
					stmt.execute(sql);
					result = true;
				}
				catch (SQLException e)
				{
					System.err.println(e.getSQLState());
					System.err.println("Unable to insert User: " + u.toString());
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
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try (Statement stmt = conn.createStatement())
			{
				String username = vui.getUsername();
				String password = vui.getPassword();
				
				String sql = "SELECT Password,FirstName,LastName,IndexedRecords FROM User WHERE Username = \'" + username + "\';";
				
				String tempPass = null;
				String tempFirst = null;
				String tempLast = null;
				int tempIndexedRecords = -1;
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
					{
						tempPass = rs.getString(1);
						tempFirst = rs.getString(2);
						tempLast = rs.getString(3);
						tempIndexedRecords = rs.getInt(4);
						
						if (tempPass.equals(password))
							vuo = new ValidateUser_Output(true, tempFirst, tempLast, tempIndexedRecords);
						else
							vuo = new ValidateUser_Output(false);
					}
					else
						vuo = new ValidateUser_Output(false);
					
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete query");
			return null;
		}
		
		return vuo;
	}
	
	public ValidateUser_Output quickValidation(String u, String p) throws ServerException
	{
		ValidateUser_Input vui = new ValidateUser_Input(u,p);
		ValidateUser_Output vuo = validateUser(vui);
		
		
		return vuo;
	}
	
	public boolean hasBatchAssigned(String u) throws ServerException
	{
		boolean result = false;
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			try(Statement stmt = conn.createStatement())
			{
				String sql = "SELECT CurrentBatch FROM User WHERE Username = \'" + u + "\';";
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
					{
						if (rs.getInt(1) == -1)
							result = false;			//no batch assigned
						else
							result = true;			//batch assigned
					}
					else
						throw new ServerException(); //means the username is invalid
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete query");
			throw new ServerException();
		}
		
		return result;
	}

	public void assignBatch(String username, int batchID) throws ServerException
	{
		PreparedStatement updateImage = null;
		PreparedStatement updateUser = null;
		
		
		try (Connection conn = DatabaseConnectionPool.getConnectionPool().getCpds().getConnection())
		{
			String assignUserToBatch = "UPDATE Image SET AssignedUser=? WHERE ImageKey=?;";
			String assignBatchToUser = "UPDATE User SET CurrentBatch=? WHERE Username=?;";
			
			try
			{
				conn.setAutoCommit(false);
				
				System.out.println("#set autocommit to false");
				
				updateImage = conn.prepareStatement(assignUserToBatch);
				updateUser = conn.prepareStatement(assignBatchToUser);
				
				updateImage.setString(1, username);
				updateImage.setInt(2, batchID);
				updateImage.executeUpdate();
				updateUser.setInt(1, batchID);
				updateUser.setString(2, username);
				updateUser.executeUpdate();
				
			}
			catch (SQLException e)
			{
				System.err.println(e.getSQLState());
				
				if (conn != null)
				{
					System.err.print("Transaction is being rolled back");
					conn.rollback();
				}
				throw new ServerException();
			}
			finally
			{
				updateImage.close();
				updateUser.close();
				conn.setAutoCommit(true);
			}
		}
		catch (SQLException e)
		{
			System.err.println(e.getSQLState());
			System.err.println("Unable to complete update");
			throw new ServerException();
		}
		
	}
}
