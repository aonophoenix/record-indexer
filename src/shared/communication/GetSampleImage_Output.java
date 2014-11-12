/**
 * 
 */
package shared.communication;

/**
 * @author Justin
 *
 */
public class GetSampleImage_Output
{
	private boolean validUser;
	private boolean validProject;
	private String imageURL;
	
	//standard constructor
	public GetSampleImage_Output(String url)
	{
		validUser = true;
		validProject = true;
		imageURL = url;
	}

	//constructor if user is invalid
	public GetSampleImage_Output(boolean b)
	{
		assert b == false;
		validUser = false;
		validProject = false;
		imageURL = null;
	}
	
	//constructor if image is invalid
	public GetSampleImage_Output(boolean b, String s)
	{
		assert b == false;
		validUser = true;
		validProject = false;
		imageURL = s;
	}

	public boolean isValidUser()
	{
		return validUser;
	}
	
	public boolean isValidProject()
	{
		return validProject;
	}
	
	public String getImageURL()
	{
		return imageURL;
	}
	
	public String toString()
	{
		return validUser + "/" + imageURL;
		
	}
	
}
