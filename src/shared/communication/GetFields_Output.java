/**
 * 
 */
package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.communication.Field;

/**
 * @author Justin
 *
 */
public class GetFields_Output
{
	private boolean validUser;
	private Validity validProjectID;
	private List<Field> fields;
	
	
	public GetFields_Output()
	{
		validUser = true;
		validProjectID = Validity.VALID;
		
		fields = new ArrayList<Field>();
	}
	/**
	 * Call this constructor when the user is invalid but project ID is unknown
	 * @param u validUser
	 * @param p validProjectID (must be UNKNOWN)
	 */
	public GetFields_Output(boolean b)
	{
		assert b == false;
		validUser = b;
		validProjectID = Validity.UNKNOWN;
	}
	/**
	 * Call this constructor when the user is valid but project ID is not
	 * @param u validUser
	 * @param p validProjectID (must be INVALID)
	 */
	public GetFields_Output(boolean u, Validity p)
	{
		assert u == true;
		assert p == Validity.INVALID;
		validUser = u;
		validProjectID = Validity.INVALID;
	}

	public boolean addField(int pid, int fid, String title)
	{
		if (pid < 0 || fid < 0 || title.isEmpty())
			return false;
		Field temp = new Field(pid, fid, title);
		
		return fields.add(temp);
	}
	
	/**
	 * @return the validUser
	 */
	public boolean isValidUser()
	{
		return validUser;
	}
	/**
	 * @return the validProjectID
	 */
	public Validity isValidProject()
	{
		return validProjectID;
	}
	
	public Field[] getFieldArray()
	{
		Field[] array = null;
		
		array = fields.toArray(new Field[0]);
		
		return array;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if (fields == null)
			return "FAILED\n";
		
		for (int i = 0; i < fields.size(); i++)
			sb.append(fields.get(i).toString());
		
		return sb.toString();
	}
}
