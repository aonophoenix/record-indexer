/**
 * 
 */
package shared.communication;

/**
 * A container class used to hold input for the Communicator GetSampleImage method.
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			username : String
 * 			password : String
 * 			projectID : int
 * </pre>
 * @author Justin Shattuck
 */
public class GetSampleImage_Input
{
	private String username;
	private String password;
	private int projectID;
	
	public GetSampleImage_Input(String u, String p, int id)
	{
		this.username = u;
		this.password = p;
		this.projectID = id;
	}
	
	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
	
	public int getProjectID()
	{
		return projectID;
	}
	
	public String toString()
	{
		String result = "";
		result = "Username: " + username + "//Password: " + password + "//ProjectID" + projectID;
		
		return result;
	}
	
}