package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.rmi.ServerException;

import server.database.DatabaseAccessor;
import shared.communication.*;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Create and run a server
 * 
 * @author Justin Shattuck
 *
 */
public class Server
{
	
	/**
	 * the port number
	 */
	private static int port;
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private HttpServer server;
	private XStream xmlStream = new XStream(new DomDriver());
	private DatabaseAccessor databaseAccessor;
	
	private Server()
	{
		return;
	}
	
	private void run()
	{
		try
		{
			this.server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e)
		{
			System.out.println("Could not initialize HttpServer: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		
		databaseAccessor = new DatabaseAccessor();
		
		
		
		server.setExecutor(null);

		server.createContext("/" + ProxyServer.DOWNLOAD_BATCH	, downloadBatchHandler);
		server.createContext("/" + ProxyServer.DOWNLOAD_FILE	, downloadFileHandler);
		server.createContext("/" + ProxyServer.GET_FIELDS		, getFieldsHandler);
		server.createContext("/" + ProxyServer.GET_PROJECTS		, getProjectsHandler);
		server.createContext("/" + ProxyServer.GET_SAMPLE_IMAGE	, getSampleImageHandler);
		server.createContext("/" + ProxyServer.SEARCH			, searchHandler);
		server.createContext("/" + ProxyServer.SUBMIT_BATCH		, submitBatchHandler);
		server.createContext("/" + ProxyServer.VALIDATE_USER	, validateUserHandler);
		server.createContext("/"								, emptyHandler);
		
		System.out.println("starting server...");
		server.start();
	}
	
	
	
	//list of handlers....

	private HttpHandler emptyHandler = new HttpHandler()
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("GOT HERE");
		}
	};

	private HttpHandler downloadBatchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("downloadBatchHandler");
			
		}
	};
	private HttpHandler downloadFileHandler = new HttpHandler()
	{
		
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			//record-indexer\data\Records\images
			System.out.println("downloadFileHandler");
//			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//		//	xmlStream.toXML(contacts, exchange.getResponseBody());
//			exchange.getResponseBody().close();
		}
	};
	private HttpHandler getFieldsHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("getFieldsHandler");
			
		}
		
	};
	private HttpHandler getProjectsHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("getProjectsHandler");
			
		}
		
	};
	private HttpHandler getSampleImageHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("getSampleImageHandler");
			
		}
		
	};
	private HttpHandler searchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("searchHandler");
			
		}
		
	};
	private HttpHandler submitBatchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("submitBatchHandler");
			
		}
		
	};
	private HttpHandler validateUserHandler = new HttpHandler()
	{
		
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ValidateUser_Input vui = (ValidateUser_Input) xmlStream.fromXML(exchange.getRequestBody());
			
			System.out.println("validateUserHandler");
			System.out.println(vui.toString());
			
			
			
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		}
		
	};
	
//	private HttpHandler getAllContactsHandler = new HttpHandler()
//	{
//		@Override
//		public void handle(HttpExchange exchange) throws IOException
//		{
//			//should contact the server facade
//			try
//			{
//				//get the stuff
//			}
//			catch(ServerException e)
//			{
//				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
//			}
//			
//			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//			//convert to XML
//			exchange.getResponseBody().close();
//			
//		}
//	};
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
			
		new Server().run();
		
		
	}

}
