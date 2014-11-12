/**
 * 
 */
package shared.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin
 *
 */
public class DownloadBatch_Output
{
	private boolean validUser;
	private boolean batchAlreadyAssigned;
	private boolean availableBatches;
	private boolean validProject;
	private String reason;
	
	private int batchID;			//image table
	private int projectID;			//image table
	private String imageURL;		//image table
	
	private int firstYCoord;		//project table
	private int recordHeight;		//project table
	private int recordsPerImage;	//project table
	
	private List<DownloadBatchField> fields;
	
	
	//invalid user
	public DownloadBatch_Output(boolean v)
	{
		assert v == false;
		this.validUser = false;
		
		batchAlreadyAssigned = false;
		availableBatches = false;
		validProject = false;
		batchID = -1;
		projectID = -1;
		imageURL = null;
		firstYCoord = -1;
		recordHeight = -1;
		recordsPerImage = -1;
		fields = null;
	}
	//valid user; batch already assigned, no available batches, or invalid project ID
	public DownloadBatch_Output(boolean ba, boolean ab)
	{
		this.validUser = true;
		
		batchAlreadyAssigned = ba;
		availableBatches = ab;
		
		validProject = false;
		batchID = -1;
		projectID = -1;
		imageURL = null;
		firstYCoord = -1;
		recordHeight = -1;
		recordsPerImage = -1;
		fields = null;
		
	}
	
	public DownloadBatch_Output(boolean v, int bid, int pid, String iURL, int y, int h, int rpi)
	{
		assert v == true;
		validUser = true;
		batchAlreadyAssigned = false;
		availableBatches = true;
		validProject = true;
		batchID = bid;
		projectID = pid;
		imageURL = iURL;
		firstYCoord = y;
		recordHeight = h;
		recordsPerImage = rpi;
		fields = new ArrayList<DownloadBatchField>();
	}
	public boolean isValid()
	{
		return validUser;
	}
	
	public boolean isBatchAlreadyAssigned()
	{
		return batchAlreadyAssigned;
	}
	
	public boolean isAvailableBatches()
	{
		return availableBatches;
	}
	
	public boolean isValidProject()
	{
		return validProject;
	}
	
	public boolean addField(int fid, int fn, String fname, String hURL, int xc, int w, String kvURL)
	{
		boolean result = true;
		DownloadBatchField temp = new DownloadBatchField(fid, fn, fname, hURL, xc, w, kvURL);
		
		result = fields.add(temp);
		
		return result;
	}
	
	public boolean addField(DownloadBatchField temp)
	{
		boolean result = true;
		result = fields.add(temp);
		
		return result;
	}
	
	public boolean getResult()
	{
		return validUser && !batchAlreadyAssigned && availableBatches;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(batchID + "\n");
		sb.append(projectID + "\n");
		sb.append(imageURL + "\n");
		sb.append(firstYCoord + "\n");
		sb.append(recordHeight + "\n");
		sb.append(recordsPerImage + "\n");
		
		for (int i = 0; i < fields.size(); i++)
		{
			sb.append(fields.toString());
		}
		
		return sb.toString();
	}
	
	
}
