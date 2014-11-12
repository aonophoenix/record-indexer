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
	 * Creates a new SeachInput.
	 * @param u username
	 * @param p password
	 * @param f fields
	 * @param v values
	 */
	public Search_Input(String u, String p, ArrayList<Integer> f, ArrayList<String> v)
	{
		this.username = u;
		this.password = p;
		this.fields = f;
		this.values = v;
	}
	
	/**
	 * Attempts to add a fieldID
	 * @param f unique field identifier
	 * @return true if successful, false otherwise
	 */
	public boolean addField(int f)
	{
		assert fields != null;
		boolean success = false;
		success = fields.add(f);
		return success;
	}

	/**
	 * Attempts to add a value
	 * @param v value
	 * @return true if successful, false otherwise
	 */
	public boolean addValue(String v)
	{
		assert values != null;
		boolean success = false;
		success = values.add(v);
		return success;
	}
	

	/**
	 * Obvious.
	 * @return the username
	 */
	public String getUsername()
	{
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
	 * @return an array of Integer
	 */
	public Integer[] getFields()
	{
		assert fields != null;
		return fields.toArray(new Integer[0]);
	}


	/**
	 * Obvious.
	 * @return an array of String
	 */
	public String[] getValues()
	{
		assert values != null;
		return values.toArray(new String[0]);
	}
	
	public String toString()
	{
		String result = "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("Fields: ");
		for (int i = 0; i < fields.size(); i++)
		{
			if (i != 0)
				sb.append(",");
			sb.append(fields.get(i));
		}
		sb.append("Values: ");
		for (int i = 0; i < values.size(); i++)
		{
			if (i != 0)
				sb.append(",");
			sb.append(values.get(i));
		}
		
		
		result = "Username: " + username + "//Password: " + password + "//" + sb.toString();
		
		return result;
	}
	
	
}
