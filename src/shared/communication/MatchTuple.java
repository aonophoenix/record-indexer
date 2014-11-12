/**
 * 
 */
package shared.communication;

/**
 * A container class used to hold matched tuples for the Communicator Search_Output method.
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
public class MatchTuple
{
	private final int imageID;
	private final String imageUrl;
	private final int rowNumber;
	private final int fieldID;
	
	public MatchTuple(int iid, String url, int row, int fid)
	{
		this.imageID = iid;
		this.imageUrl = url;
		this.rowNumber = row;
		this.fieldID = fid;
	}

	/**
	 * @return the imageID
	 */
	public int getImageID() {
		return imageID;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * @return the fieldID
	 */
	public int getFieldID() {
		return fieldID;
	}
	
	public String toString()
	{
		return imageID + "\n" + imageUrl + "\n" + rowNumber + "\n" + fieldID;
	}
	
	public boolean equals(Object obj)
	{
		boolean result = obj != null &&
				obj instanceof MatchTuple;
		
		if (result)
		{
			result = (this.imageID == ((MatchTuple) obj).getImageID() &
					  this.imageUrl.equalsIgnoreCase(((MatchTuple) obj).getImageUrl()) &
					  this.rowNumber == ((MatchTuple) obj).getRowNumber() &
					  this.fieldID == ((MatchTuple) obj).getFieldID());
			
		}
		
		return result;
	}
	
}
