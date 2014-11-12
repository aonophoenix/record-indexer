/**
 * 
 */
package shared.communication;

/**
 * A container class used to hold input for the Communicator GetProjects method.
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			username : String
 * 			password : String
 * </pre>
 * @author Justin Shattuck
 */
public class GetProjects_Input
{
	private String username;
	private String password;
	
	public GetProjects_Input(String u, String p)
	{
		this.username = u;
		this.password = p;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
	
	public String toString()
	{
		String result = "";
		result = "Username: " + username + "//Password: " + password;
		
		return result;
	}
	
}
