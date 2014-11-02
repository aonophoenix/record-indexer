/**
 * 
 */
package shared.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
public class Value
{
		@XStreamImplicit(itemFieldName = "value")
		private ArrayList<String> values;
		
		public Value(String ... strings)
		{
			this.values = new ArrayList<String>();
			for (int i = 0; i < strings.length; i++)
				add(strings[i]);
			
		}
		
		private void add(String s)
		{
			values.add(s);
		}
		
		public List<String> getValues()
		{
			return values;
		} 
		
		public String toString()
		{
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < values.size(); i++)
				result.append("\tValue: " + values.get(i) + "\n");
			return result.toString();
		}
}
