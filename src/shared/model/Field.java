package shared.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * A single field from which we will extract information
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			fieldKey : int
 * 			title : String
 * 			xCoord : int
 * 			width : int
 * 			helpHtml : String
 * 			knownData : String
 * </pre>
 * @author Justin Shattuck
 */
@XStreamAlias("field")
public class Field
{
	/**
	 * Unique identifier associated with this field.
	 */
	@XStreamOmitField
	private int fieldKey;
	/**
	 * Field title.
	 */
	private String title;
	/**
	 * Horizontal displacement from left side in pixels.
	 */
	@XStreamAlias("xcoord")
	private int xCoord;
	/**
	 * Width of column in pixels.
	 */
	private int width;
	/**
	 * String representing relative location of HTML help file.
	 */
	@XStreamAlias("helphtml")
	private String helpHtml;
	/**
	 * String representing relative location of known data file (if applicable).
	 */
	@XStreamAlias("knowndata")
	private String knownData;
	
	
	/**
	 * Creates a field with a unique key, title, x-coordinate, and width.
	 * @param key unique identifier
	 * @param t title
	 * @param x starting x-coordinate in pixels
	 * @param w column width in pixels
	 */
	public Field(int key, String t, int x, int w)
	{
		this.fieldKey = key;
		this.title = t;
		this.xCoord = x;
		this.width = w;
	}
	
	/**
	 * Associates an HTML help file with this field.
	 * @param fileString the relative location of the file
	 * @return true if association worked, false otherwise
	 */
	public boolean addHelpHtml(String fileString)
	{
		boolean success = false;
		this.helpHtml = fileString;
		if (helpHtml != null)
			success = true;
		return success;
	}
	
	/**
	 * Associates an known data file with this field.
	 * @param fileString the relative location of the file
	 * @return true if association worked, false otherwise
	 */
	public boolean addKnownData(String fileString)
	{
		boolean success = false;
		this.knownData = fileString;
		if (knownData != null)
			success = true;
		return success;
	}

	/**
	 * Obvious.
	 * @return the fieldKey
	 */
	public int getFieldKey() {
		return fieldKey;
	}

	/**
	 * Obvious.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Obvious.
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * Obvious.
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Obvious.
	 * @return the helpHtml
	 */
	public String getHelpHtml() {
		return helpHtml;
	}

	/**
	 * Obvious.
	 * @return the knownData
	 */
	public String getKnownData() {
		return knownData;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("FieldID: " + this.fieldKey + "\n");
		result.append("Title: " + this.title + "\n");
		result.append("X Coordinate: " + this.xCoord + "\n");
		result.append("Width: " + this.width + "\n");
		result.append("Help HTML: " + this.helpHtml + "\n");
		result.append("Known Data: " + this.knownData + "\n");
		
		
		return result.toString();
	}
	
	
	
}
