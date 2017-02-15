import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataBase.CSVFileReader;
import dataBase.DataObject;
import dataBase.DayStrings;
import dataBase.ListStringArraysToDJObject;
import preprocessing.*;

public class GetWordCountsTest {
	private List<DataObject> dayStoriesList = new ArrayList<DataObject>();
	private List<DataObject> multipleDaysStories = new ArrayList<DataObject>();
	private List<DataObject> repeatedStories = new ArrayList<DataObject>();
	private List<DataObject> DJList;
	private PreprocessingController preprocessingController = new PreprocessingController();

	@Before
	public void setupDateDJOpening(){
		CSVFileReader reader = new CSVFileReader();
		List<String[]> stringList = reader.readFile("Data/DJIA_table.csv");
		ListStringArraysToDJObject conversion = new ListStringArraysToDJObject();
		DJList = conversion.stringtoDataObject(stringList);
	}
	public void setupSingleDayScenario(){
		//Set up single day scenario
		String dateString = "2016-07-01";
		java.sql.Date date = java.sql.Date.valueOf(dateString);
		preprocessingController = new PreprocessingController();
		String[] storyList = new String[2];
		storyList[0] = "IMF chief backs Athens as permanent Olympic host";
		storyList[1] = "The president of France says if Brexit won, so can Donald Trump";
		DayStrings dayStories = new DayStrings(date,storyList);
		dayStoriesList.add(dayStories);
	}
	
	public void setupMultipleDaysScenario(){
		String dateString = "2016-07-01";
		java.sql.Date date = java.sql.Date.valueOf(dateString);
		preprocessingController = new PreprocessingController();
		String[] storyList = new String[2];
		storyList[0] = "IMF chief backs Athens as permanent Olympic host";
		storyList[1] = "The president of France says if Brexit won, so can Donald Trump";
		DayStrings dayStories = new DayStrings(date,storyList);
		dayStoriesList.add(dayStories);		
		String dateString2 = "2016-06-30";
		java.sql.Date date2 = java.sql.Date.valueOf(dateString2);
		String[] storyList2 = new String[1];
		storyList2[0] = "Jamaica proposes marijuana dispensers for tourists at airports following legalisation: The kiosks and desks would give people a license to purchase up to 2 ounces of the drug to use during their stay";
		DayStrings dayStories2 = new DayStrings(date2,storyList2);
		multipleDaysStories.add(dayStories);
		multipleDaysStories.add(dayStories2);
	}
		
	public void setupRepeatedStoryScenario(){
		String dateString3 = "2016-06-29";
		java.sql.Date date3 = java.sql.Date.valueOf(dateString3);
		String[] storyList3 = new String[2];
		storyList3[0] = "IMF chief backs Athens as permanent Olympic host";
		storyList3[1] = "IMF chief Athens as permanent Olympic host";
		DayStrings dayStories3 = new DayStrings(date3,storyList3);
		repeatedStories.add(dayStories3);		
	}
	
	@Test
	public void getWordCountsReturnsCorrectSizeListOfIntArrays() throws FileNotFoundException, SQLException, ParseException {
		setupSingleDayScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(dayStoriesList);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0).length, 16);
		
	}
	
	@Test
	public void correctWordCountsMultipleDays() throws FileNotFoundException, SQLException, ParseException {
		setupMultipleDaysScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(multipleDaysStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(1).length, wordCounts.get(0).length);
		
	}
	
	@Test
	public void repeatedWordsNotCountedTwice() throws FileNotFoundException, SQLException, ParseException {
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0).length, 9);

	}
	
	@Test
	public void headerDOWIsCorrect() throws FileNotFoundException, SQLException, ParseException{
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0)[0],17456);
	}
	
	@Test
	public void headerDayIsCorrect() throws SQLException, FileNotFoundException, ParseException{
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0)[1],29);
	}
	
	@Test
	public void headerMonthIsCorrect() throws SQLException, FileNotFoundException, ParseException{
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0)[2],6);
	}
	
	@Test
	public void headerYearIsCorrect() throws SQLException, FileNotFoundException, ParseException{
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0)[3],2016);
	}
	
	@Test
	public void firstUniqueWordCountIsCorrect() throws SQLException, FileNotFoundException, ParseException{
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0)[4],2);
	}
	
	@Test
	public void secondUniqueWordHasCount1() throws SQLException, FileNotFoundException, ParseException{
		setupRepeatedStoryScenario();
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions, DJList);
		assertEquals(wordCounts.get(0)[5],1);
	}

}
