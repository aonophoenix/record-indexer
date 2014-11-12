/**
 * 
 */
package shared.communication;

/**
 * @author Justin
 *
 */
public class SubmitBatch_Output
{
	private boolean validUser;
	private Validity validImage;
	private boolean success;
	
	//standard constructor
	public SubmitBatch_Output(boolean u, Validity v, boolean s)
	{
		validUser = u;
		validImage = v;
		success = s;
	}
	
	public boolean isValidUser()
	{
		return validUser;
	}
	
	public Validity isValidImage()
	{
		return validImage;
	}
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public String toString()
	{
		return validUser + "/" + validImage + "/" + success;
	}
	
}
