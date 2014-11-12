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
public class GetProjects_Output
{
	private boolean validUser;
	private List<Integer> projectIDs;
	private List<String> projectTitles;
	
	public GetProjects_Output()
	{
		validUser = true;
		projectIDs = new ArrayList<Integer>();
		projectTitles = new ArrayList<String>();
	}
	
	public GetProjects_Output(boolean b)
	{
		assert b == false;
		validUser = b;
		projectIDs = null;
		projectTitles = null;
		}
	
	public boolean addProject(int i, String t)
	{
		assert projectIDs != null;
		assert projectTitles != null;
		boolean result = true;
//		int tempIsize = projectIDs.size();
//		int tempTsize = projectTitles.size();
		
		projectIDs.add(i);
		projectTitles.add(t);
		
		return result;
	}
	
	public boolean isValid()
	{
		return validUser;
	}
	
	public Integer[] getArrayOfProjectIDs()
	{
		return projectIDs.toArray(new Integer[0]);
	}
	
	public String[] getArrayOfProjectTitles()
	{
		return projectTitles.toArray(new String[0]);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		assert projectIDs.size() == projectTitles.size();
		assert projectIDs != null;
		assert projectTitles != null;
		
		for (int i = 0; i < projectIDs.size(); i++)
		{
			sb.append(projectIDs.get(i) + "_");
			sb.append(projectTitles.get(i) + "//");
		}
		
		
		return sb.toString();
	}
	
}
