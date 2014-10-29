package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.rmi.ServerException;

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
		
		
		server.setExecutor(null);
		
		server.createContext("/" + ProxyServer.GET_ALL_CONTACTS , getAllContactsHandler);
		server.createContext("/" + ProxyServer.ADD_CONTACT		, addContactHandler);
		server.createContext("/" + ProxyServer.UPDATE_CONTACT	, updateContactHandler);
		server.createContext("/" + ProxyServer.DELETE_CONTACT	, deleteContactHandler);
		server.createContext("/"								, emptyHandler);
		
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
	
	private HttpHandler getAllContactsHandler = new HttpHandler()
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			//should contact the server facade
			try
			{
				//get the stuff
			}
			catch(ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			//convert to XML
			exchange.getResponseBody().close();
			
		}
	};
	
	private HttpHandler addContactHandler = new HttpHandler()
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			//xmlStream.fromXML(exchange.getResponseBody());
			
			//should contact the server facade
			try
			{
				//get the stuff
			}
			catch(ServerException e)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			}
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			//convert to XML
			exchange.getResponseBody().close();
			
		}
	};
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		
		
		
		
	}

}
