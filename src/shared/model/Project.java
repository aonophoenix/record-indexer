package shared.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * An image from which we will extract information
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			projectKey : int
 * 			title : String;
 * 			recordsPerImage : int
 * 			firstYCoord : int
 * 			recordHeight : int
 * 			fields : List&lt;Field&gt;
 * 			images : List&lt;Image&gt;
 * </pre>
 * @author Justin Shattuck
 */
@XStreamAlias("project")
public class Project
{
	/**
	 * Unique identifier associated with this project.
	 */
	@XStreamOmitField
	private int projectKey;
	/**
	 * Title of this project.
	 */
	private String title;
	/**
	 * Number of records per batch/image.
	 */
	@XStreamAlias("recordsperimage")
	private int recordsPerImage;
	/**
	 * Vertical displacement from top in pixels.
	 */
	@XStreamAlias("firstycoord")
	private int firstYCoord;
	/**
	 * Height of each record in pixels.
	 */
	@XStreamAlias("recordheight")
	private int recordHeight;
	/**
	 * List of fields for this project.
	 */
	private List<Field> fields;
	/**
	 * List of all batch/images for this project.
	 */
	private List<Image> images;
	
	/**
	 * Creates a project with a unique key, title, number of records per batch/image,
	 * first y-coordinate, and record height.
	 * @param key unique identifier
	 * @param t project title
	 * @param r records per batch/image
	 * @param y vertical displacement in pixels
	 * @param h height of each record in pixels
	 */
	public Project(int key, String t, int r, int y, int h)
	{
		this.projectKey = key;
		this.title = t;
		this.recordsPerImage = r;
		this.firstYCoord = y;
		this.recordHeight = h;
		fields = new ArrayList<Field>();
		images = new ArrayList<Image>();
	}
	
	
	/**
	 * Attempts to add a field.
	 * @param f field to add
	 * @return true if successful, false otherwise
	 */
	public boolean  addField(Field f)
	{
		boolean result = false;
		if (fields.contains(f))
			return result;
		else
		{
			fields.add(f);
			result = true;
		}
		return result;
	}
	
	/**
	 * Attempts to add an image.
	 * @param i image to add
	 * @return true if successful, false otherwise
	 */
	public boolean addImage(Image i)
	{
		boolean result = false;
		if (images.contains(i))
			return result;
		else
		{
			images.add(i);
			result = true;
		}
		return result;
	}
	
	
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("ProjectID: " + this.projectKey + "\n");
		result.append("Title: " + this.title + "\n");
		result.append("Records Per Image: " + this.recordsPerImage + "\n");
		result.append("First Y Coordinate: " + this.firstYCoord + "\n");
		result.append("Record Height: " + this.recordHeight + "\n");
		for (int i = 0; i < fields.size(); i++)
			result.append(fields.get(i).toString());
		for (int i = 0; i < images.size(); i++)
			result.append(images.get(i).toString());
		
		return result.toString();
	}


	public int getProjectKey()
	{
		return projectKey;
	}


	public void setProjectKey(int projectKey)
	{
		this.projectKey = projectKey;
	}


	public String getTitle()
	{
		return title;
	}


	public int getRecordsPerImage()
	{
		return recordsPerImage;
	}


	public int getFirstYCoord()
	{
		return firstYCoord;
	}


	public int getRecordHeight()
	{
		return recordHeight;
	}


	public List<Field> getFields()
	{
		return fields;
	}


	public List<Image> getImages()
	{
		return images;
	}
	
}
