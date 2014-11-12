package shared.communication;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A container class used to hold output for the Communicator Search method.
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			matches : List<MatchTuple>
 * </pre>
 *  
 *  
 * @author Justin Shattuck
 */
public class Search_Output
{
	private boolean validUser;
	private boolean matchFound;
	private List<MatchTuple> matches;
	
	
	/**
	 * Creates a SearchOutput object to be passed back to the Communicator Search method.
	 * Assumes the user is valid
	 */
	public Search_Output()
	{
		validUser = true;
		matches = new ArrayList<MatchTuple>();
	}
	/**
	 * Creates a SearchOutput object to be passed back to the Communicator Search method.
	 * @param b valid user boolean (must be false)
	 */
	public Search_Output(boolean b)
	{
		assert b == false;
		validUser = false;
		matches = null;
	}
	
	public Search_Output(boolean u, boolean f)
	{
		assert u == true;
		validUser = true;
		matchFound = false;
		matches = null;
	}
	
	/**
	 * Adds a MatchTuple object to the list.
	 * @param iid unique image identifier
	 * @param url string representing image url
	 * @param row row number
	 * @param fid unique field identifier
	 */
	public boolean addMatch(int iid, String url, int row, int fid)
	{
		assert matches != null;
		boolean result = false;
		MatchTuple temp = new MatchTuple(iid, url, row, fid);
		result = matches.add(temp);
		if (result)
			matchFound = true;
		return result;
	}

	/**
	 * @return a boolean indicating whether or not the user is valid
	 */
	public boolean isValidUser()
	{
		return validUser;
	}

	/**
	 * @return an array of MatchTuple objects
	 * Assumes user is valid
	 */
	public MatchTuple[] getMatches()
	{
		assert matches != null;
		return matches.toArray(new MatchTuple[0]);
	}
	
	public boolean isMatchFound()
	{
		return matchFound;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		
		if (matchFound == true)
			for (int i = 0; i < matches.size(); i++)
				sb.append(matches.get(i));
		
		else return "no matches";
		
		return sb.toString();
	}
	
	
	
}
