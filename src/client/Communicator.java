package client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import shared.model.*;

/**
 * Communicates with the server to upload/download files and retrieve information.
 * <pre>
 * 		<b>Domain:</b>
 * </pre>
 * @author Justin Shattuck
 *
 */
public class Communicator
{

	
	
	public HttpURLResponse doPost(String commandName, Object postData)
	{
		assert commandName != null;
		assert postData != null;
		
		try 
		{
			URL url = new URL(URL_PREFIX + "/" + commandName);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			
			result.setResponseCode(connection.getResponseCode());
			result.setResponseLength(connection.getContentLength());
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				if (connection.getContentLength() == 0)
				{
					result.setResponseBody(xmlStream.fromXML(connection.getInputStream()));
			
				}
			}
			else
				throw new ClientException(String.format("doPost failed: %s (http code %d)", commandName, connection.getResponseCode()));
		}
		catch (IOException e)
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
	}
	
	
	private static final String SERVER_HOST 
	
	
	
	public void validateUser(String u, String pw)
	{
		
	}
	
	public void getProjects(String u, String pw)
	{
		
	}
	
	public void getSampleImage(String u, String pw, int pid)
	{
		
	}
	
	public void downloadBatch(String u, String pw, int pid)
	{
		
	}
	
	public void submitBatch(String u, String pw, int imgID, ArrayList<Record> r)
	{
		
	}
	
	public void getFields(String u, String pw, int pid)
	{
		
	}
	
	public void Search(String u, String pw, ArrayList<Integer> fields, ArrayList<String> values)
	{
		
	}
	
	public void downloadFile(String f)
	{
		
	}
	
	
	
	
	
}
