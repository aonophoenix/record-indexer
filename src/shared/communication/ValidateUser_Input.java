package shared.communication;

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
public class ValidateUser_Input
{
	private String username;
	private String password;
	
	public ValidateUser_Input(String u, String p)
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
