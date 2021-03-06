package client.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import client.ClientException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;


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

	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 8080;
	private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT + "/";
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	private XStream xmlStream;
	
	private static Communicator singleton = null;
	
	
	
	public static Communicator getSingleton()
	{
		if(singleton == null) {
			singleton = new Communicator();
		}
		return singleton;
	}
	
	private Communicator()
	{
		xmlStream = new XStream(new DomDriver());
	}
	
	public HttpURLResponse doGet(String commandName) throws ClientException {
		assert commandName != null && commandName.length() > 0;
		
		HttpURLResponse result = new HttpURLResponse();
		try {
			URL url = new URL(URL_PREFIX + "/" + commandName);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			
			result.setResponseCode(connection.getResponseCode());
			result.setResponseLength(connection.getContentLength());
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if(connection.getContentLength() == 0)
				{
					result.setResponseBody(xmlStream.fromXML(connection.getInputStream()));
				}
			} else {
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
						commandName, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
		}
		return result;
	}
	
	public HttpURLResponse doPost(String commandName, Object postData) throws ClientException
	{
		assert commandName != null;
		assert postData != null;
		
//		System.out.println("#creating a new HttpURLResponse");
		HttpURLResponse result = new HttpURLResponse();
//		System.out.println("##Command Name: " + commandName);
		
		try {
			URL url = new URL(URL_PREFIX + commandName);
//			System.out.println("###" + url);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//			System.out.println("####connection made");
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			
//			System.out.println("#####" + postData.toString());
//			boolean xmlnull = xmlStream == null;
//			System.out.println("###### XMLStream: " + xmlnull);
//			boolean connectionoutstreamnull = connection.getOutputStream() == null;
//			System.out.println("#######connection output stream:" + connectionoutstreamnull);
//			System.out.println("########");
//			System.out.println();
			
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();						//this sends the data
			
			
			result.setResponseCode(connection.getResponseCode());
			result.setResponseLength(connection.getContentLength());
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				if(connection.getContentLength() == -1)
				{
					result.setResponseBody(xmlStream.fromXML(connection.getInputStream()));
					
				}
			}
			else
			{
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
											commandName, connection.getResponseCode()));
			}
		}
		catch (IOException e)
		{
			System.out.println("communicator ioexception");
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
		return result;
	}
	
	public ValidateUser_Output validateUser(ValidateUser_Input vui) throws ClientException
	{
		HttpURLResponse response = null;
		
		response = doPost(ProxyServer.VALIDATE_USER, vui);
		
		return (ValidateUser_Output) response.getResponseBody();
		
	}
	
	public GetProjects_Output getProjects(GetProjects_Input gpi) throws ClientException
	{
		HttpURLResponse response = null;
		
		response = doPost(ProxyServer.GET_PROJECTS, gpi);
		return (GetProjects_Output) response.getResponseBody();
		
	}
	
	public GetSampleImage_Output getSampleImage(GetSampleImage_Input gsii) throws ClientException
	{
		HttpURLResponse response = null;
		
		response = doPost(ProxyServer.GET_SAMPLE_IMAGE, gsii);
		return (GetSampleImage_Output) response.getResponseBody();
	}
	
	public DownloadBatch_Output downloadBatch(DownloadBatch_Input dbi) throws ClientException
	{
		HttpURLResponse response = null;
		response = doPost(ProxyServer.DOWNLOAD_BATCH, dbi);
		
		return (DownloadBatch_Output) response.getResponseBody();
	}
	
	public SubmitBatch_Output submitBatch(String u, String pw, int imgID, ArrayList<RecordValues> r) throws ClientException
	{
		HttpURLResponse response = null;
		SubmitBatch_Input sbi = new SubmitBatch_Input(u, pw, imgID, r);
		
		response = doPost(ProxyServer.SUBMIT_BATCH, sbi);
		
		return (SubmitBatch_Output) response.getResponseBody();	}
	
	public GetFields_Output getFields(String u, String pw, int pid) throws ClientException
	{
		HttpURLResponse response = null;
		GetFields_Input gfi = new GetFields_Input(u, pw, pid);
		
		response = doPost(ProxyServer.GET_FIELDS, gfi);
		
		return (GetFields_Output) response.getResponseBody();
	}
	
	public Search_Output search(String u, String pw, ArrayList<Integer> fields, ArrayList<String> values) throws ClientException
	{
		HttpURLResponse response = null;
		Search_Input si = new Search_Input(u, pw, fields, values);
		
		response = doPost(ProxyServer.SEARCH, si);
		
		return (Search_Output) response.getResponseBody();
		
	}
	
	public void downloadFile(String f)
	{
		
	}

	
	
	
	
	
}
