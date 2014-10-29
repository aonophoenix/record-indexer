package shared.communication;

import java.net.URL;

/**
 * A container class used to hold output for the Communicator Search method.
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			imageID : int
 * 			imageUrl : String
 * 			rowNumber : int
 * 			fieldID : int
 * </pre>
 *  
 *  
 * @author Justin Shattuck
 */
public class Search_Output
{
	private int imageID;
	private String imageUrl;
	private int rowNumber;
	private int fieldID;
	
	
	/**
	 * Creates a SearchOutput object to be passed back to the Communicator Search method.
	 * @param iid unique image identifier
	 * @param url string representing image url
	 * @param row row number
	 * @param fid unique field identifier
	 */
	public Search_Output(int iid, String url, int row, int fid)
	{
		this.imageID = iid;
		this.imageUrl = url;
		this.rowNumber = row;
		this.fieldID = fid;
	}


	/**
	 * Obvious.
	 * @return the imageID
	 */
	public int getImageID() {
		return imageID;
	}


	/**
	 * Obvious.
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}


	/**
	 * Obvious.
	 * @return the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}


	/**
	 * Obvious.
	 * @return the fieldID
	 */
	public int getFieldID() {
		return fieldID;
	}
	
	
	
}
