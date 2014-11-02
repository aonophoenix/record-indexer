package shared.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * A completely superfluous wrapper class that exists only to allow the generated xml 
 * to match the specs.
 * <pre>
 * 		<b>Domain:</b>
 * 			recordKey : int;
 * 			values : Value;
 * </pre>
 * @author Justin Shattuck
 *
 */
@XStreamAlias("record")
public class Record
{
	/**
	 * Unique identifier associated with this field.
	 */
	@XStreamOmitField
	private int recordKey;
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
	 * @return a Value
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

	public int getRecordKey()
	{
		return recordKey;
	}

	public void setRecordKey(int recordKey)
	{
		this.recordKey = recordKey;
	}
	
}
