/**
 * 
 */
package shared.communication;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

/**
 * @author Justin
 *
 */
public class RecordValues
{
	private List<String> values;
	
	public RecordValues()
	{
		values = new ArrayList<String>();
	}
	
	public boolean addValue(String s)
	{
		return values.add(s);
	}
	
	public List<String> getValues()
	{
		return values;
	}
	
	public String toString()
	{
		if (values == null)
			return "";
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < values.size(); i++)
			sb.append(values.get(i));
		
		return sb.toString();
	}
	
}
