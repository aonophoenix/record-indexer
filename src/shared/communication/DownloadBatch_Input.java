package shared.communication;

/**
 * A container class used to hold input for the Communicator DownloadBatch method.
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			username : String
 * 			password : String
 * 			fields : List&lt;Integer&gt;
 * 			values : List&lt;String&gt;
 * </pre>
 * @author Justin Shattuck
 */
public class DownloadBatch_Input
{
	private String username;
	private String password;
	private int projectID;
	
	
	/**
	 * Creates a DownloadBatch_Input object to be passed to the server.
	 * 
	 * @param u username
	 * @param p password
	 * @param pid unique project identifier
	 */
	public DownloadBatch_Input(String u, String p, int pid)
	{
		this.username = u;
		this.password = p;
		this.projectID = pid;
	}


	/**
	 * Obvious.
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Obvious.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Obvious.
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	
	
}
