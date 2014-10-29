package shared.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.IndexerData;
import shared.model.*;

public class ExporterTest
{
	protected IndexerData idex1;
	protected IndexerData idex2;
	protected IndexerDataExporter exporter1;
	protected IndexerDataExporter exporter2;
	protected File testOutput;
	protected File testRecords;
	protected PrintWriter printer1;
	protected PrintWriter printer2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		
//		testOutput = new File("data/output.xml");
//		if (!testOutput.canWrite())
//			throw new Exception();
		
//		ExporterTest.exporter1 = new 
//		printer1 = new PrintWriter();
		
//		testRecords = new File("data/testRecords.xml");
//		if (!testOutput.canWrite())
//			throw new Exception();
//		printer2 = new PrintWriter(testRecords);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		
	}

	@Before
	public void setUp() throws Exception
	{
		idex1 = new IndexerData();
		User user1 = new User("test1", "test1", "Test", "One", "email1", 0);
		User user2 = new User("test2", "test2", "Test", "Two", "email2", 3);
		idex1.addUser(user1);
		idex1.addUser(user2);
		Project project1 = new Project(-1, "Draft Records", 7, 195, 65);
		Field field1 = new Field(-1, "Last Name", 75, 325);
		field1.addHelpHtml("fieldhelp/last_name.html");
		field1.addKnownData("knowndata/draft_last_names.txt");
		Field field2 = new Field(-1, "First Name", 400, 325);
		field2.addHelpHtml("fieldhelp/first_name.html");
		field2.addKnownData("knowndata/draft_first_names.txt");
		project1.addField(field1);
		project1.addField(field2);
		Image image1 = new Image("images/draft_image12.png");
		Record record1 = new Record("POTTER", "DOUGLAS", "23", "NATIVE HAWAIIAN");
		Record record2 = new Record("LINDSEY", "KURT", "28", "ALASKA NATIVE");
		image1.addRecord(record1);
		image1.addRecord(record2);
		project1.addImage(image1);
		idex1.addProject(project1);
		
		XmlFileImporter tempImporter = new XmlFileImporter("data/records.xml");
		System.out.println(tempImporter.parseFile());
		tempImporter.convert();
		idex2 = tempImporter.getIndexerData();
		
		printer1 = new PrintWriter("data/testOutput.xml");
		printer2 = new PrintWriter("data/testRecords.xml");
		
	}

	@After
	public void tearDown() throws Exception
	{
		printer1.close();
		printer2.close();
	}

	
	@Test
	public void testGenerateXml()
	{
		exporter1 = new IndexerDataExporter(idex1);
		printer1.print(exporter1.generateXml());
		
		exporter2 = new IndexerDataExporter(idex2);
		printer2.print(exporter2.generateXml());
		
		
	}
	

}
