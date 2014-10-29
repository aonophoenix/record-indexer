/**
 * 
 */
package shared.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import server.IndexerData;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author Justin
 *
 */
public class IndexerDataExporter
{
	private XStream xstream;
	private String xmlFileString;
	private File xmlFile;
	private String xmlString;
	private IndexerData indexerData;
	
	public IndexerDataExporter(IndexerData idex)
	{
		this.xstream = new XStream(new DomDriver());
		this.indexerData = idex;
		
		//generateXml();
		
	}

	private boolean checkFile()
	{
		xmlFile = new File(xmlFileString);
		return xmlFile.canWrite();
	}

	public String generateXml()
	{
		assert indexerData != null;
		
		xstream.processAnnotations(IndexerData.class);
		
		this.xmlString = xstream.toXML(indexerData);
		
		return this.xmlString;
	}
	
	public void exportToFile(String fileString) throws FileNotFoundException
	{
		this.xmlFileString = fileString;
		if (checkFile())
			throw new FileNotFoundException();
		
	}

	/**
	 * @return the xstream
	 */
	public XStream getXstream()
	{
		return xstream;
	}

	/**
	 * @return the xmlFileString
	 */
	public String getXmlFileString() {
		return xmlFileString;
	}

	/**
	 * @return the xmlFile
	 */
	public File getXmlFile() {
		return xmlFile;
	}

	/**
	 * @return the xmlString
	 */
	public String getXmlString() {
		return xmlString;
	}

	/**
	 * @return the indexerData
	 */
	public IndexerData getIndexerData() {
		return indexerData;
	}
	
	
	
	
	
	
}
