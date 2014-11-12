/**
 * 
 */
package shared.communication;

/**
 * @author Justin
 *
 */
public class Field
{
	private int projectID;
	private int fieldID;
	private String fieldTitle;
	
	
	public Field(int pid, int fid, String title)
	{
		projectID = pid;
		fieldID = fid;
		fieldTitle = title;
	}

	public int getProjectID()
	{
		return projectID;
	}

	public int getFieldID()
	{
		return fieldID;
	}

	public String getFieldTitle()
	{
		return fieldTitle;
	}
	
	public String toString()
	{
		return projectID + "\n" + fieldID + "\n" + fieldTitle + "\n";
	}
	
}
