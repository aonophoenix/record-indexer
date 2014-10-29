package shared.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import server.IndexerData;
import shared.model.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XmlFileImporter
{
	private XStream xstream;
	private String xmlFileString;
	private File xmlFile;
	private StringBuilder xmlString;
	private IndexerData indexerData;
	
	public XmlFileImporter(String filepath) throws FileNotFoundException
	{
		this.xstream = new XStream(new StaxDriver());
		this.xmlFileString = filepath;
		//this.xmlFile = new File(xmlFileString);
		if (!checkFile())
			throw new FileNotFoundException();
		
	}
	
	private boolean checkFile()
	{
		this.xmlFile = new File(xmlFileString);
		return this.xmlFile.canRead();
	}
	
	public boolean parseFile()
	{
		boolean success = false;
		Scanner scan = null;
		
		try
		{
			BufferedReader buff = new BufferedReader(new FileReader(xmlFile));
			scan = new Scanner(buff);
			this.xmlString = new StringBuilder();
			while (scan.hasNextLine())
			{
				this.xmlString.append(scan.nextLine());
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found.");
			return success;
		}
		success = true;
		
		return success;
	}
	
	/**
	 * Obvious.
	 * @return the xmlString
	 */
	public String getXmlString()
	{
		return xmlString.toString();
	}

	/**
	 * Obvious.
	 * @return the xmlFileString
	 */
	public String getXmlFileString()
	{
		return xmlFileString;
	}

	/**
	 * Obvious.
	 * @return the xmlFile
	 */
	public File getXmlFile()
	{
		return xmlFile;
	}

	public void convert()
	{
		assert xmlString != null;
		xstream.processAnnotations(IndexerData.class);
		
		this.indexerData = (IndexerData) xstream.fromXML(xmlString.toString());
	}

	/**
	 * @return the indexerData
	 */
	public IndexerData getIndexerData()
	{
		return indexerData;
	}

	/**
	 * @param indexerData the indexerData to set
	 */
	public void setIndexerData(IndexerData indexerData) {
		this.indexerData = indexerData;
	}
	
}
