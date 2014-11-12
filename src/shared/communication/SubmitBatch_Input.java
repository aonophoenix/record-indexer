package shared.communication;

import java.util.List;

/**
 * A container class used to hold input for the Communicator ValidateUser method.
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			username : String
 * 			password : String
 * </pre>
 * @author Justin Shattuck
 */
public class SubmitBatch_Input
{
	private String username;
	private String password;
	private int batchID;
	private List<RecordValues> recordValues;
	
	
	public SubmitBatch_Input(String u, String p, int bid, List<RecordValues> list)
	{
		this.username = u;
		this.password = p;
		this.batchID = bid;
		this.recordValues = list;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
	/**
	 * @return the batchID
	 */
	public int getBatchID()
	{
		return batchID;
	}
	/**
	 * @return an ArrayList of recordValues objects
	 */
	public List<RecordValues> getRecordValues()
	{
		return recordValues;
	}
	
	public String toString()
	{
		String result = "";
		result = "Username: " + username + "//Password: " + password + "//BatchID: " + batchID;
		
		return result;
	}

	
}
