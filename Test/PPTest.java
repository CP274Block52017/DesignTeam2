import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import preprocessing.*;

public class PPTest {
	private List<DayStrings> dayStoriesList = new ArrayList<DayStrings>();
	private PreprocessingController preprocessingController = new PreprocessingController();

	@Before
	public void setup(){
		String dateString = "2016-07-01";
		java.sql.Date date = java.sql.Date.valueOf(dateString);
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
		dayStoriesList.add(dayStories2);
	}
	
	@Test
	public void dayStoryObjectCreatedForDayOneContainsCorrectStoryAtPositionOne() {
		DayStrings firstDayStories = dayStoriesList.get(0);
		String[] storyList = firstDayStories.getStringArray();
		assertEquals(storyList[0],"IMF chief backs Athens as permanent Olympic host");
	}
	
	@Test
	public void dayStoryObjectCreatedForDayOneContainsCorrectDate() {
		DayStrings firstDayStories = dayStoriesList.get(0);
		java.sql.Date date = firstDayStories.getDate();
		String correctDateString = "2016-07-01";
		java.sql.Date correctDate = java.sql.Date.valueOf(correctDateString);
		assertEquals(date,correctDate);
	}
	
	@Test
	public void removePrepositionsForDayOneWithTwoStoriesReturnsCorrectStringArray() throws FileNotFoundException {
		List<DayStrings> removedPrepsList = preprocessingController.removePrepositions(dayStoriesList);
		DayStrings firstDay = removedPrepsList.get(0);
		String[] removedPrepArray= firstDay.getStringArray();
		String[] correctArray = new String[12];
		correctArray[0] = "IMF";
		correctArray[1] = "backs";
		correctArray[2] = "Athens";
		correctArray[3] = "Olympic";
		correctArray[4] = "host";
		correctArray[5] = "president";
		correctArray[6] = "France";
		correctArray[7] = "says";
		correctArray[8] = "Brexit";
		correctArray[9] = "won";
		correctArray[10] = "Donald";
		correctArray[11] = "Trump";
		assertArrayEquals(removedPrepArray,correctArray);
	}
	
	@Test
	public void removePrepositionsForDayTwoWithOneStoryReturnsCorrectStringArray() throws FileNotFoundException {
		List<DayStrings> removedPrepsList = preprocessingController.removePrepositions(dayStoriesList);
		DayStrings dayTwo = removedPrepsList.get(1);
		String[] removedPrepArray= dayTwo.getStringArray();
		String[] correctArray = new String[18];
		correctArray[0] = "Jamaica";
		correctArray[1] = "proposes";
		correctArray[2] = "marijuana";
		correctArray[3] = "dispensers";
		correctArray[4] = "tourists";
		correctArray[5] = "airports";
		correctArray[6] = "following";
		correctArray[7] = "legalisation";
		correctArray[8] = "kiosks";
		correctArray[9] = "desks";
		correctArray[10] = "give";
		correctArray[11] = "people";
		correctArray[12] = "license";
		correctArray[13] = "purchase";
		correctArray[14] = "ounces";
		correctArray[15] = "drug";
		correctArray[16] = "use";
		correctArray[17] = "stay";
		assertArrayEquals(removedPrepArray,correctArray);
	}
	
	@Test
	public void getWordCountsForListOfDayStrings() {
		List<DayWordCount> dayWordCounts = preprocessingController.getWordCounts(dayStoriesList);
		for(int i = 0; i < dayWordCounts.size(); i++){
			WordCount[] wordCounts = dayWordCounts.get(i).getWordCountArray();
			for(int j = 0; j < wordCounts.length; j++){
				System.out.println(wordCounts[j].getWord() + " " + wordCounts[j].getWordCount());
			}
		}
		assertTrue(dayStoriesList.size() == 2);
	}
}
