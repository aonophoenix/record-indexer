package shared.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * An image from which we will extract information
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			imageID : int
 * 			fileString : String
 * 			records : List&lt;Record&gt;
 * 			isComplete : boolean
 * 			assignedUser : User
 * </pre>
 * @author Justin Shattuck
 */
@XStreamAlias("image")
public class Image
{
	private static int counter = 0;
	/**
	 * Unique identifier associated with this batch/image.
	 */
	@XStreamOmitField
	private int imageID;
	/**
	 * The name of the png file that holds the image.
	 */
	@XStreamAlias("file")
	private String fileString;
	/**
	 * The list of extracted records corresponding from the image.
	 * Each extracted record corresponds to a row in the image.
	 */
	@XStreamAlias("records")
	private List<Record> records;
	/**
	 * A boolean indicating whether or not this batch/image is complete.
	 */
	@XStreamOmitField
	private boolean isComplete;
	/**
	 * The user currently assigned to this batch/image.
	 */
	@XStreamOmitField
	private User assignedUser;
	/**
	 * Creates an image from a filename and a list of records. The list of 
	 * records is usually empty.
	 * @param f the name of the png file containing the image
	 */
	public Image(String f)
	{
		this.imageID = -1;
		this.fileString = f;
		this.records = new ArrayList<Record>();
		this.isComplete = false;
		this.assignedUser = null;
	}
	
	public boolean addRecord(Record r)
	{
		boolean result = false;
		if (records.contains(r))
			return result;
		else
		{
			records.add(r);
			result = true;
		}
		return result;
	}
	
	/**
	 * Obvious.
	 * @return the filename
	 */
	public String getFileString()
	{
		return fileString;
	}
	/**
	 * Get the records.
	 * @return the records
	 */
	public List<Record> getRecords()
	{
		return records;
	}
	/**
	 * Get the completion status.
	 * @return boolean
	 */
	public boolean getIsComplete()
	{
		return isComplete;
	}
	/**
	 * Get the currently assigned user.
	 * @return the user
	 */
	public User getAssignedUser()
	{
		return assignedUser;
	}
	
	public boolean isThisAssigned()
	{
		if (assignedUser == null)
			return false;
		else
			return true;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("BatchID: " + this.imageID + "\n");
		result.append("File: " + this.fileString + "\n");
		
		counter++;
		if (records != null)
		{
			result.append("Records:\n");
		for (int i = 0; i < records.size(); i++)
			result.append("\tValues:\n" + records.get(i).toString());
		}
		result.append("Complete?: " + this.isComplete + "\n");
		result.append("Assigned User: " + isThisAssigned() + "\n");
		
		return result.toString();
	}

	public int getImageID()
	{
		return imageID;
	}

	public void setImageID(int imageID)
	{
		this.imageID = imageID;
	}
	
}
