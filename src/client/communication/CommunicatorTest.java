package client.communication;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.ClientException;
import shared.communication.*;

public class CommunicatorTest
{
	private Communicator communicator;
	private final String VALID_USERNAME = "test1";
	private final String INVALID_USERNAME = "test5";
	private final String VALID_PASSWORD = "test1";
	private final String INVALID_PASSWORD = "test3";
	private final String VALID_USERNAME2 = "test2";
	private final String VALID_PASSWORD2 = "test2";
	private final int PROJECT_ALL = -1;
	private final int PROJECT_1 = 1;
	private final int PROJECT_2 = 2;
	private final int PROJECT_3 = 3;
	private final int PROJECT_9 = 9;
	private final int MAGIC_NUMBER_THIRTEEN = 13;
	
	
	@Before
	public void setUp() throws Exception
	{
		communicator = Communicator.getSingleton();
		
	}

	@After
	public void tearDown() throws Exception
	{
		//communicator.doGet("tomare");
		
	}

	@Test
	public void testDoGet()
	{
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDoPost()
	{
		fail("Not yet implemented"); // TODO
	}

//	@Test
	public void testValidateUser()
	{
		ValidateUser_Input validUserI = new ValidateUser_Input(VALID_USERNAME, VALID_PASSWORD);
		ValidateUser_Input wrongUsernameI = new ValidateUser_Input(INVALID_USERNAME, VALID_PASSWORD);
		ValidateUser_Input wrongPasswordI = new ValidateUser_Input(VALID_USERNAME, INVALID_PASSWORD);
		ValidateUser_Input emptyUsernameI = new ValidateUser_Input("", VALID_PASSWORD);
		ValidateUser_Input emptyPasswordI = new ValidateUser_Input(VALID_USERNAME, "");
		ValidateUser_Output validUserO = null;
		ValidateUser_Output wrongUsernameO = null;
		ValidateUser_Output wrongPasswordO = null;
		ValidateUser_Output emptyUsernameO = null;
		ValidateUser_Output emptyPasswordO = null;
		
		
		try
		{
			validUserO = communicator.validateUser(validUserI);
			
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			wrongUsernameO = communicator.validateUser(wrongUsernameI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			wrongPasswordO = communicator.validateUser(wrongPasswordI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			emptyUsernameO = communicator.validateUser(emptyUsernameI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			emptyPasswordO = communicator.validateUser(emptyPasswordI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		
		assertFalse(validUserO == null);
		assertTrue(wrongUsernameO == null);
		assertTrue(wrongPasswordO == null);
		assertTrue(emptyUsernameO == null);
		assertTrue(emptyPasswordO == null);
	}
	
//	@Test
	public void testGetProjects()
	{
		GetProjects_Input getProjects_validUserI =     new GetProjects_Input(VALID_USERNAME, VALID_PASSWORD);
		GetProjects_Input getProjects_wrongUsernameI = new GetProjects_Input(INVALID_USERNAME, VALID_PASSWORD);
		GetProjects_Input getProjects_wrongPasswordI = new GetProjects_Input(VALID_USERNAME, INVALID_PASSWORD);
		GetProjects_Input getProjects_emptyUsernameI = new GetProjects_Input("", VALID_PASSWORD);
		GetProjects_Input getProjects_emptyPasswordI = new GetProjects_Input(VALID_USERNAME, "");
		GetProjects_Output getProjects_validUserO = null;
		GetProjects_Output getProjects_wrongUsernameO = null;
		GetProjects_Output getProjects_wrongPasswordO = null;
		GetProjects_Output getProjects_emptyUsernameO = null;
		GetProjects_Output getProjects_emptyPasswordO = null;
		
		try
		{
			getProjects_validUserO = communicator.getProjects(getProjects_validUserI);
			
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			getProjects_wrongUsernameO = communicator.getProjects(getProjects_wrongUsernameI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			getProjects_wrongPasswordO = communicator.getProjects(getProjects_wrongPasswordI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			getProjects_emptyUsernameO = communicator.getProjects(getProjects_emptyUsernameI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			getProjects_emptyPasswordO = communicator.getProjects(getProjects_emptyPasswordI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		
		assertFalse(getProjects_validUserO == null);
		assertTrue(getProjects_wrongUsernameO == null);
		assertTrue(getProjects_wrongPasswordO == null);
		assertTrue(getProjects_emptyUsernameO == null);
		assertTrue(getProjects_emptyPasswordO == null);
		
		String[] tempTitles = null;
		
		//Get an array of the projects
		//We have to check all indexes against all the titles
		//because we don't know the order the database will
		//give them to us
		tempTitles = getProjects_validUserO.getArrayOfProjectTitles();
		assertTrue(tempTitles[0].equals("1890 Census") || 
				   tempTitles[1].equals("1890 Census") || 
				   tempTitles[2].equals("1890 Census"));		
		assertTrue(tempTitles[0].equals("1900 Census") || 
				   tempTitles[1].equals("1900 Census") || 
				   tempTitles[2].equals("1900 Census"));
		assertTrue(tempTitles[0].equals("Draft Records") || 
				   tempTitles[1].equals("Draft Records") || 
				   tempTitles[2].equals("Draft Records"));
		
	}

//	@Test
	public void testGetSampleImage()
	{
		GetSampleImage_Input  getSampleImage_validUserI =		new GetSampleImage_Input(VALID_USERNAME, VALID_PASSWORD, 1);
		GetSampleImage_Input  getSampleImage_wrongUsernameI =	new GetSampleImage_Input(INVALID_USERNAME, VALID_PASSWORD, 1);
		GetSampleImage_Input  getSampleImage_wrongPasswordI =	new GetSampleImage_Input(VALID_USERNAME, INVALID_PASSWORD, 1);
		GetSampleImage_Input  getSampleImage_wrongProjectIdI =	new GetSampleImage_Input(VALID_USERNAME, VALID_PASSWORD, 7);
		GetSampleImage_Output getSampleImage_validUserO =		null;
		GetSampleImage_Output getSampleImage_wrongUsernameO =	null;
		GetSampleImage_Output getSampleImage_wrongPasswordO =	null;
		GetSampleImage_Output getSampleImage_wrongProjectIdO =	null;
		
		try
		{
			getSampleImage_validUserO = communicator.getSampleImage(getSampleImage_validUserI);
			
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			getSampleImage_wrongUsernameO = communicator.getSampleImage(getSampleImage_wrongUsernameI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			getSampleImage_wrongPasswordO = communicator.getSampleImage(getSampleImage_wrongPasswordI);
			fail("you should have thrown an exception");
		}
		catch (ClientException e)
		{
			//good
		}
		try
		{
			getSampleImage_wrongProjectIdO = communicator.getSampleImage(getSampleImage_wrongProjectIdI);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		
		assertFalse(getSampleImage_validUserO == null);
		assertTrue(getSampleImage_wrongUsernameO == null);
		assertTrue(getSampleImage_wrongPasswordO == null);
		assertFalse(getSampleImage_wrongProjectIdO == null);
		assertFalse(getSampleImage_wrongProjectIdO.isValidProject());
		
		assertTrue(getSampleImage_validUserO.getImageURL().contains("1890_image") ||
				   getSampleImage_validUserO.getImageURL().contains("1900_image") ||
				   getSampleImage_validUserO.getImageURL().contains("draft_image"));
		
		
	}

//	@Test
	public void testDownloadBatch()
	{
		DownloadBatch_Input downloadBatch_validUserI =		new DownloadBatch_Input(VALID_USERNAME, VALID_PASSWORD, 1);
		DownloadBatch_Input downloadBatch_batchAssignedI =	new DownloadBatch_Input(VALID_USERNAME, VALID_PASSWORD, 1);
		DownloadBatch_Input downloadBatch_wrongProjectIdI =	new DownloadBatch_Input(VALID_USERNAME2, VALID_PASSWORD2, 7);
		
		DownloadBatch_Output downloadBatch_validUserO =		null;
		DownloadBatch_Output downloadBatch_batchAssignedO =	null;
		DownloadBatch_Output downloadBatch_wrongProjectIdO =	null;
		
		try
		{
			downloadBatch_validUserO = communicator.downloadBatch(downloadBatch_validUserI);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			downloadBatch_batchAssignedO = communicator.downloadBatch(downloadBatch_batchAssignedI);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			downloadBatch_wrongProjectIdO = communicator.downloadBatch(downloadBatch_wrongProjectIdI);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		
		assertTrue(downloadBatch_validUserO.isValid());
		assertTrue(downloadBatch_validUserO.isAvailableBatches());
		assertFalse(downloadBatch_validUserO.isBatchAlreadyAssigned());
		
		assertTrue(downloadBatch_batchAssignedO.isValid());
		assertTrue(downloadBatch_batchAssignedO.isAvailableBatches());
		assertTrue(downloadBatch_batchAssignedO.isBatchAlreadyAssigned());
		
		assertFalse(downloadBatch_wrongProjectIdO.isValidProject());
		
	}

//	@Test
	public void testGetFields()
	{
		GetFields_Output getFields_validUser_project1O = null;
		GetFields_Output getFields_validUser_projectsO = null;
		GetFields_Output getFields_validUser_project9O = null;
		
		try
		{
			getFields_validUser_project1O = communicator.getFields(VALID_USERNAME, VALID_PASSWORD, PROJECT_1);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			getFields_validUser_projectsO = communicator.getFields(VALID_USERNAME, VALID_PASSWORD, PROJECT_ALL);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			getFields_validUser_project9O = communicator.getFields(VALID_USERNAME, VALID_PASSWORD, PROJECT_9);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		
//		getFields_validUser_project1O
//		getFields_validUser_projectsO
//		getFields_validUser_project9O
		
		boolean[] validUser1_fieldID_booleanMatchArray = {false,false,false,false};
		boolean[] validUser1_title_booleanMatchArray = {false,false,false,false};
		Field[] array = getFields_validUser_project1O.getFieldArray();
		
		assertTrue(getFields_validUser_project1O.isValidUser());
		assertTrue(getFields_validUser_project1O.isValidProject() == Validity.VALID);
		assertTrue(array.length == 4);
		assertTrue(array[0].getProjectID() == PROJECT_1);
		for (int i = 0; i < array.length; i++)
		{
			if (array[i].getFieldID() == 1)
				validUser1_fieldID_booleanMatchArray[0] = true;
			else if (array[i].getFieldID() == 2)
				validUser1_fieldID_booleanMatchArray[1] = true;
			else if (array[i].getFieldID() == 3)
				validUser1_fieldID_booleanMatchArray[2] = true;
			else if (array[i].getFieldID() == 4)
				validUser1_fieldID_booleanMatchArray[3] = true;
		}
		for (int i = 0; i < validUser1_fieldID_booleanMatchArray.length; i++)
			assertTrue(validUser1_fieldID_booleanMatchArray[i] == true);
		
		for (int i = 0; i < array.length; i++)
		{
			if (array[i].getFieldTitle().equals("Last Name"))
				validUser1_title_booleanMatchArray[0] = true;
			else if (array[i].getFieldTitle().equals("First Name"))
				validUser1_title_booleanMatchArray[1] = true;
			else if (array[i].getFieldTitle().equals("Age"))
				validUser1_title_booleanMatchArray[2] = true;
			else if (array[i].getFieldTitle().equals("Ethnicity"))
				validUser1_title_booleanMatchArray[3] = true;
		}
		for (int i = 0; i < validUser1_title_booleanMatchArray.length; i++)
			assertTrue(validUser1_title_booleanMatchArray[i] == true);
		
		assertTrue(getFields_validUser_projectsO.isValidUser());
		assertTrue(getFields_validUser_projectsO.isValidProject() == Validity.VALID);
		assertTrue(getFields_validUser_projectsO.getFieldArray().length == MAGIC_NUMBER_THIRTEEN);
		
		assertTrue(getFields_validUser_project9O.isValidUser());
		assertTrue(getFields_validUser_project9O.isValidProject() == Validity.INVALID);
		
		
		
		
	}

//	@Test
	public void testSearch()
	{
		Search_Output search_validUserO1 =	null;
		Search_Output search_validUserO2 =	null;
		Search_Output search_noResultsO =	null;
		
		/**
		 * The following ArrayList are being prepped for this SQL query:
		 * 
		 * SELECT Value.Value,Image.ImageKey,Image.FileString,Record.RowNum,Field.FieldKey
		 * FROM Value
		 *  INNER JOIN Record
		 *   ON Record.RecordKey=Value.RecordKey
		 *  INNER JOIN Image
		 *   ON Image.ImageKey=Record.ImageKey
		 *  INNER JOIN Field
		 *   ON Field.FieldKey=Value.FieldKey
		 *  WHERE Value.FieldKey IN (4)
		 *   AND Value.Value IN ('BLACK');
		 */
		ArrayList<Integer> search_validUser1_fields = new ArrayList<Integer>();
		search_validUser1_fields.add(4);	//Draft - Ethnicity
		ArrayList<String> search_validUser1_values = new ArrayList<String>();
		search_validUser1_values.add("BLACK");
		
		/**
		 * The following ArrayList are being prepped for this SQL query:
		 * 
		 * SELECT Value.Value,Image.ImageKey,Image.FileString,Record.RowNum,Field.FieldKey
		 * FROM Value
		 *  INNER JOIN Record
		 *   ON Record.RecordKey=Value.RecordKey
		 *  INNER JOIN Image
		 *   ON Image.ImageKey=Record.ImageKey
		 *  INNER JOIN Field
		 *   ON Field.FieldKey=Value.FieldKey
		 *  WHERE Value.FieldKey IN (3,4)
		 *   AND Value.Value IN (22,'ALASKA NATIVE');
		 */
		ArrayList<Integer> search_validUser2_fields = new ArrayList<Integer>();
		search_validUser2_fields.add(3);	//Draft - Age
		search_validUser2_fields.add(4);	//Draft - Ethnicity
		ArrayList<String> search_validUser2_values = new ArrayList<String>();
		search_validUser2_values.add("22");
		search_validUser2_values.add("ALASKA NATIVE");
		
		
		/**
		 * The following ArrayList are being prepped for this SQL query:
		 * 
		 * SELECT Value.Value,Image.ImageKey,Image.FileString,Record.RowNum,Field.FieldKey
		 * FROM Value
		 *  INNER JOIN Record
		 *   ON Record.RecordKey=Value.RecordKey
		 *  INNER JOIN Image
		 *   ON Image.ImageKey=Record.ImageKey
		 *  INNER JOIN Field
		 *   ON Field.FieldKey=Value.FieldKey
		 *  WHERE Value.FieldKey IN (9)
		 *   AND Value.Value IN ('FEMALE');
		 */
		ArrayList<Integer> search_noResults_fields = new ArrayList<Integer>();
		search_noResults_fields.add(9);		//1900 Census - Gender
		ArrayList<String> search_noResults_values = new ArrayList<String>();
		search_noResults_values.add("FEMALE");
		
		try
		{
			search_validUserO1 = communicator.search(VALID_USERNAME, VALID_PASSWORD, search_validUser1_fields, search_validUser1_values);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			search_validUserO2 = communicator.search(VALID_USERNAME, VALID_PASSWORD, search_validUser2_fields, search_validUser2_values);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		try
		{
			search_noResultsO = communicator.search(VALID_USERNAME, VALID_PASSWORD, search_noResults_fields, search_noResults_values);
		}
		catch (ClientException e)
		{
			fail("you should not have thrown this");
		}
		
		ArrayList<MatchTuple> validUser1TestTuples = new ArrayList<MatchTuple>();
		validUser1TestTuples.add(new MatchTuple(1,"images/draft_image0.png",7,4));
		validUser1TestTuples.add(new MatchTuple(2,"images/draft_image1.png",4,4));
		validUser1TestTuples.add(new MatchTuple(3,"images/draft_image2.png",1,4));
		validUser1TestTuples.add(new MatchTuple(4,"images/draft_image3.png",3,4));
		validUser1TestTuples.add(new MatchTuple(4,"images/draft_image3.png",4,4));
		validUser1TestTuples.add(new MatchTuple(4,"images/draft_image3.png",6,4));
		
		boolean[] validUser1_booleanMatchArray = {false,false,false,false,false,false};
		boolean[] validUser2_booleanMatchArray = {false,false,false,false,false,false};
//		boolean[] validUser_booleanResultArray = {true,true,true,true,true,true};
		
		MatchTuple[] validUserO1_resultArray = search_validUserO1.getMatches();
		
		
		for (int i = 0; i < validUser1TestTuples.size(); i++)
		{
			for (int j = 0; j < validUserO1_resultArray.length; j++)
			{
				if (validUser1TestTuples.get(i).equals(validUserO1_resultArray[j]))
					validUser1_booleanMatchArray[i] = true;
			}
		}
		
		for (int i =0; i < validUser1_booleanMatchArray.length; i++)
			assertTrue(validUser1_booleanMatchArray[i]);
		
		
		ArrayList<MatchTuple> validUser2TestTuples = new ArrayList<MatchTuple>();
		validUser2TestTuples.add(new MatchTuple(2,"images/draft_image1.png",2,4));
		validUser2TestTuples.add(new MatchTuple(3,"images/draft_image2.png",3,4));
		validUser2TestTuples.add(new MatchTuple(4,"images/draft_image3.png",2,3));
		validUser2TestTuples.add(new MatchTuple(4,"images/draft_image3.png",7,4));
		validUser2TestTuples.add(new MatchTuple(5,"images/draft_image4.png",2,3));
		validUser2TestTuples.add(new MatchTuple(5,"images/draft_image4.png",3,3));
		
		MatchTuple[] validUserO2_resultArray = search_validUserO1.getMatches();
		
		
		for (int i = 0; i < validUser1TestTuples.size(); i++)
		{
			for (int j = 0; j < validUserO1_resultArray.length; j++)
			{
				if (validUser2TestTuples.get(i).equals(validUserO2_resultArray[j]))
					validUser2_booleanMatchArray[i] = true;
			}
		}
		
		for (int i =0; i < validUser1_booleanMatchArray.length; i++)
			assertTrue(validUser1_booleanMatchArray[i]);
		
		assertFalse(search_noResultsO.isMatchFound());
		
	}

	@Test
	public void testDownloadFile()
	{
		
		
		
		
		
	}

}
