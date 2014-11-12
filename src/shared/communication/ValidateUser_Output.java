/**
 * 
 */
package shared.communication;

/**
 * @author Justin
 *
 */
public class ValidateUser_Output
{
	private boolean validUser;
	private String firstName;
	private String lastName;
	private int numberOfRecords;
	
	public ValidateUser_Output(boolean v)
	{
		assert v == false;
		this.validUser = false;
	}
	public ValidateUser_Output(boolean v, String f, String l, int n)
	{
		assert v == true;
		this.validUser = true;
		this.firstName = f;
		this.lastName = l;
		this.numberOfRecords = n;
	}
	public boolean isValid()
	{
		return validUser;
	}
	public String getFirstName()
	{
		assert this.validUser == true;
		return firstName;
	}
	public String getLastName()
	{
		assert this.validUser == true;
		return lastName;
	}
	public int getNumberOfRecords()
	{
		assert this.validUser == true;
		return numberOfRecords;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(validUser + "\n");
		sb.append(firstName + "\n");
		sb.append(lastName + "\n");
		sb.append(numberOfRecords + "\n");
		
		
		return sb.toString();
	}
	
	
}
