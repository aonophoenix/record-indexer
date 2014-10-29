package shared.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A completely superfluous wrapper class that exists only to allow the generated xml 
 * to match the specs.
 * <pre>
 * 		<b>Domain:</b>
 * 			values : Value;
 * </pre>
 * @author Justin Shattuck
 *
 */
@XStreamAlias("record")
public class Record
{
	/**
	 * List of all values in this record.
	 */
	@XStreamAlias("values")
	private Value values;
	
	
	public Record(String ... strings)
	{
		values = new Value(strings);
	}
	
	/**
	 * Obvious.
	 * @return the List of values
	 */
	public Value getValues()
	{
		return values;
	}
	
//	public String getValueAt(int i)
//	{
//		String result = null;
//		if (i >= values.size() || i < 0)
//			return null;
//		else
//			result = values.get(i).getValue();
//		return result;
//	}
	
	public String toString()
	{
//		StringBuilder result = new StringBuilder();
//		for (int i = 0; i < values.size(); i++)
//			result.append("\tValue: " + values.get(i).getValue() + "\n");
//		
//		return result.toString();
		
		return values.toString();
	}
	
}
