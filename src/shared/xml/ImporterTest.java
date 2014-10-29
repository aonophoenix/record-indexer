package shared.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.IndexerData;

public class ImporterTest
{
	protected static XmlFileImporter xmlFileImporter;
	protected static IndexerData idex;
	protected static String xmlString;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		ImporterTest.xmlFileImporter = new XmlFileImporter("data/records.xml");
		assertTrue(ImporterTest.xmlFileImporter.getXmlFile().canRead());
		System.out.println("completed setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		
	}

	@Before
	public void setUp() throws Exception
	{
	//	assertTrue(ImporterTest.importer.getXmlFile().exists());
		ImporterTest.xmlFileImporter.parseFile();
	//	assertTrue(ImporterTest.importer.getXmlString() != null);
		ImporterTest.xmlString = ImporterTest.xmlFileImporter.getXmlString();
		System.out.println("completed setUp");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testImporter()
	{
		XmlFileImporter importer1 = null;
		XmlFileImporter importer2 = null;
		try
		{
			importer1 = new XmlFileImporter("data/records.xml");
			importer2 = new XmlFileImporter("data/tester.xml");
		}
		catch (FileNotFoundException e)
		{
			
		}
		assertFalse(importer1 == null);
		assertTrue(importer2 == null);
		//System.out.println(ImporterTest.xmlString);
		
		
	}
	
	
	@Test
	public void testXmlToObject()
	{
		assertTrue(ImporterTest.xmlFileImporter.getIndexerData() == null);
		assertTrue(ImporterTest.xmlFileImporter.getXmlFile().canRead());
//		assertTrue(ImporterTest.importer.getXmlString() == null);
		System.out.println(ImporterTest.xmlFileImporter.getXmlString());
		ImporterTest.xmlFileImporter.convert();
		assertTrue(ImporterTest.xmlFileImporter.getIndexerData() != null);
		
		System.out.println(ImporterTest.xmlFileImporter.getIndexerData().toString());
		
		
		
	}
	
}
