package shared.communication;

import java.util.ArrayList;

/**
 * A container class used to hold input for the Communicator Search method.
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
public class Search_Input
{
	private String username;
	private String password;
	private ArrayList<Integer> fields;
	private ArrayList<String> values;
	
	
	/**
	 * Creates a new SeachInput.
	 * @param u username
	 * @param p password
	 */
	public Search_Input(String u, String p)
	{
		this.username = u;
		this.password = p;
		this.fields = new ArrayList<Integer>();
		this.values = new ArrayList<String>();
	}
	
	
	/**
	 * Attempts to add a fieldID
	 * @param f unique field identifier
	 * @return true if successful, false otherwise
	 */
	public boolean addField(int f)
	{
		boolean success = false;
		
		return success;
	}

	/**
	 * Attempts to add a value
	 * @param v value
	 * @return true if successful, false otherwise
	 */
	public boolean addValue(String v)
	{
		boolean success = false;
		
		return success;
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
	 * @return the fields
	 */
	public ArrayList<Integer> getFields() {
		return fields;
	}


	/**
	 * Obvious.
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}
	
	
	
	
}
