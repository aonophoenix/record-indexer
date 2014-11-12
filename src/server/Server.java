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
	
	@SuppressWarnings("restriction")
	private void run() throws server.ServerException
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
		
		databaseAccessor = new DatabaseAccessor();		//opens default database
		
		server.setExecutor(null);

		server.createContext("/" + ProxyServer.DOWNLOAD_BATCH	, downloadBatchHandler);
		server.createContext("/" + ProxyServer.DOWNLOAD_FILE	, downloadFileHandler);
		server.createContext("/" + ProxyServer.GET_FIELDS		, getFieldsHandler);
		server.createContext("/" + ProxyServer.GET_PROJECTS		, getProjectsHandler);
		server.createContext("/" + ProxyServer.GET_SAMPLE_IMAGE	, getSampleImageHandler);
		server.createContext("/" + ProxyServer.SEARCH			, searchHandler);
		server.createContext("/" + ProxyServer.SUBMIT_BATCH		, submitBatchHandler);
		server.createContext("/" + ProxyServer.VALIDATE_USER	, validateUserHandler);
		server.createContext("/" + "tomare"						, stop);
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
	
	private HttpHandler stop = new HttpHandler()
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("Stopping server...");
			server.stop(0);
		}
	};

	private HttpHandler downloadBatchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			DownloadBatch_Input dbi = (DownloadBatch_Input) xmlStream.fromXML(exchange.getRequestBody());
			DownloadBatch_Output dbo = null;
			try
			{
				dbo = databaseAccessor.downloadBatch((dbi));
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("downloadBatchHandler");
//			System.out.println("DBI: " + dbi);
//			System.out.println("DBO: " + dbo.isValid() + " " +
//					dbo.isValidProject() + " " +
//					dbo.isAvailableBatches() + " " +
//					dbo.isBatchAlreadyAssigned() + " " + dbo.getResult());
			
			assert dbo != null;
			
			if (dbo.isValid())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(dbo, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			
			
			exchange.close();
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
			GetFields_Input gfi = (GetFields_Input) xmlStream.fromXML(exchange.getRequestBody());
			GetFields_Output gfo = null;
			try
			{
				gfo = databaseAccessor.getFields((gfi));
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("getFieldsHandler");
//			System.out.println("GFI: " + gfi);
//			System.out.println("GFO: " + gfo);
			
			
			if (gfo.isValidUser())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(gfo, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			
			exchange.close();
		}
		
	};
	private HttpHandler getProjectsHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			GetProjects_Input gpi = (GetProjects_Input) xmlStream.fromXML(exchange.getRequestBody());
			GetProjects_Output gpo = null;
			try
			{
				gpo = databaseAccessor.getProjects((gpi));
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("getProjectsHandler");
//			System.out.println("GPI: " + gpi);
//			System.out.println("GPO: " + gpo);
			
			assert gpo != null;
			
			if (gpo.isValid())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(gpo, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			
			exchange.close();
		}
		
	};
	
	private HttpHandler getSampleImageHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			GetSampleImage_Input gsii = (GetSampleImage_Input) xmlStream.fromXML(exchange.getRequestBody());
			GetSampleImage_Output gsio = null;
			try
			{
				gsio = databaseAccessor.getSampleImage((gsii));
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("getSampleImageHandler");
//			System.out.println("GSII: " + gsii);
//			System.out.println("GSIO: " + gsio);
			
			assert gsio != null;
			
			if (gsio.isValidUser())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(gsio, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			
			exchange.close();
		}
		
	};
	private HttpHandler searchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			Search_Input si = (Search_Input) xmlStream.fromXML(exchange.getRequestBody());
			Search_Output so = null;
			
			try
			{
				so = databaseAccessor.search(si);
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("searchHandler");
//			System.out.println("SI: " + si);
//			System.out.println("SO: " + so);
			
			
			if (so.isValidUser())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(so, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			
			exchange.close();
		}
		
	};
	private HttpHandler submitBatchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			SubmitBatch_Input sbi = (SubmitBatch_Input) xmlStream.fromXML(exchange.getRequestBody());
			SubmitBatch_Output sbo = null;
			
			try
			{
				sbo = databaseAccessor.submitBatch(sbi);
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("submitBatchHandler");
			System.out.println("SI: " + sbi);
			System.out.println("SO: " + sbo);
			
			
			
			
		}
		
	};
	private HttpHandler validateUserHandler = new HttpHandler()
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ValidateUser_Input vui = (ValidateUser_Input) xmlStream.fromXML(exchange.getRequestBody());
			ValidateUser_Output vuo = null;
			try
			{
				vuo = databaseAccessor.validateUser(vui);
			}
			catch (server.ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			System.out.println("validateUserHandler");
//			System.out.println("VUI: " + vui);
//			System.out.println("VUO: " + vuo);
			
			assert vuo != null;
			
			if (vuo.isValid())
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(vuo, exchange.getResponseBody());
				
				exchange.getResponseBody().close();
			}
			else
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
			
			
			exchange.close();
		}
		
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
			
		try
		{
			new Server().run();
		}
		catch (server.ServerException e)
		{
			System.err.println("Error initializing server");
			
		}
		
		
	}

}
