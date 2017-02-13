import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import preprocessing.*;

public class GetWordCountsTest {
	private List<DayStrings> dayStoriesList = new ArrayList<DayStrings>();
	private List<DayStrings> multipleDaysStories = new ArrayList<DayStrings>();
	private List<DayStrings> repeatedStories = new ArrayList<DayStrings>();

	private PreprocessingController preprocessingController = new PreprocessingController();

	@Before
	public void setup(){
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
		
		String dateString3 = "2016-06-29";
		java.sql.Date date3 = java.sql.Date.valueOf(dateString3);
		String[] storyList3 = new String[2];
		storyList3[0] = "IMF chief backs Athens as permanent Olympic host";
		storyList3[1] = "IMF chief backs Athens as permanent Olympic host";
		DayStrings dayStories3 = new DayStrings(date3,storyList3);
		multipleDaysStories.add(dayStories3);
		
	}
	
	@Test
	public void getWordCountsReturnsCorrectSizeListOfIntArrays() throws FileNotFoundException, SQLException {
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(dayStoriesList);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions);
		assertEquals(wordCounts.size(), 1);
		assertEquals(wordCounts.get(0).length, 12);
		
	}
	
	@Test
	public void correctWordCountsMultipleDays() throws FileNotFoundException, SQLException {
		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(multipleDaysStories);
		List<int[]> wordCounts = preprocessingController.getNNList(withoutPrepositions);
		assertEquals(wordCounts.size(), 2);
		assertEquals(wordCounts.get(0).length, 30);
		System.out.println(wordCounts.get(1).length);
		assertEquals(wordCounts.get(1).length, 30);
		
	}
	
//	@Test
//	public void repeatedWordsNotCountedTwice() throws FileNotFoundException {
//		List<DayStrings> withoutPrepositions = preprocessingController.removePrepositions(repeatedStories);
//		List<int[]> wordCounts = preprocessingController.getWordCounts(withoutPrepositions);
//		assertEquals(wordCounts.size(), 1);
//		assertEquals(wordCounts.get(0).length, 5);
//		System.out.println(wordCounts.get(1).length);
//		assertEquals(wordCounts.get(1).length, 5);
//
//	}

}
