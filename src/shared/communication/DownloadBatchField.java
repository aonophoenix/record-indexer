/**
 * 
 */
package shared.communication;

/**
 * @author Justin
 *
 */
public class DownloadBatchField
{
	private boolean validUser;
	
	private int fieldID;
	private int fieldNumber;
	private String fieldName;
	private String helpURL;
	private int xCoord;
	private int width;
	private String knownValuesURL;
	
	public DownloadBatchField(int fid, int fn, String fname, String hURL, int xc, int w, String kvURL)
	{
		validUser = true;
		fieldID = fid;
		fieldNumber = fn;
		fieldName = fname;
		helpURL = hURL;
		xCoord = xc;
		width = w;
		knownValuesURL = kvURL;
	}
	
	public DownloadBatchField(boolean b)
	{
		assert b == false;
		validUser = b;
		fieldID = -1;
		fieldNumber = -1;
		fieldName = null;
		helpURL = null;
		xCoord = -1;
		width = -1;
		knownValuesURL = null;
	}

	/**
	 * @return the validUser
	 */
	public boolean isValidUser()
	{
		return validUser;
	}

	/**
	 * @return the fieldID
	 */
	public int getFieldID()
	{
		return fieldID;
	}

	/**
	 * @return the fieldNumber
	 */
	public int getFieldNumber()
	{
		return fieldNumber;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * @return the helpURL
	 */
	public String getHelpURL()
	{
		return helpURL;
	}

	/**
	 * @return the xCoord
	 */
	public int getxCoord()
	{
		return xCoord;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return the knownValuesURL
	 */
	public String getKnownValuesURL()
	{
		return knownValuesURL;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(validUser + "\n");
		sb.append(fieldID + "\n");
		sb.append(fieldNumber + "\n");
		sb.append(fieldName + "\n");
		sb.append(helpURL + "\n");
		sb.append(xCoord + "\n");
		sb.append(width + "\n");
		if (knownValuesURL.length() > 1)
			sb.append(knownValuesURL + "\n");
		
		return sb.toString();
	}
}
