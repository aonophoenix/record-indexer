/**
 * 
 */
package server.database;

import java.io.FileNotFoundException;

import server.IndexerData;
import server.ServerException;
import shared.xml.XmlFileImporter;

/**
 * @author Justin
 *
 */
public class DataImporter
{
//	private Unzip unzip;
	private XmlFileImporter importer;
	private IndexerData indexerData;
	private DatabaseAccessor databaseAccessor;
	private static String xmlFileString;
	
	public static void main(String args[])
	{
		xmlFileString = args[0];
		
		initialize();
		
	}
	
	public DataImporter() throws ServerException
	{
//		unzip = new Unzip("data/record-indexer-data.zip");
//		if (unzip.getSourceFileString() == null)
//		{
//			System.err.println("Unable to locate zip file");
//		}
		try
		{
			importer = new XmlFileImporter(xmlFileString);
			importer.parseFile();
			importer.convert();
		}
		catch (FileNotFoundException e_xmlImporter)
		{
			System.err.println("Unable to locate xml file");
		}
		
		if (importer != null)
		{
			indexerData = importer.getIndexerData();
			databaseAccessor = new DatabaseAccessor("importer.sqlite");
			System.out.println(databaseAccessor.storeXmlData(indexerData));
		}
		
	}
	
	public static void initialize()
	{
		try
		{
			DataImporter dataImporter = new DataImporter();
		}
		catch (ServerException e)
		{
			System.err.println("Unable to access database");
		}
		
		
	}
	
}
