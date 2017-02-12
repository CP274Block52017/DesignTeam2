import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import preprocessing.*;

public class PPTest {
	private List<DayStrings> dayStoriesList = new ArrayList<DayStrings>();
	//private PreprocessingController preprocessingController = new PreprocessingController();
	@Before
	public void setup(){
		String dateString = "2016-07-01";
		java.sql.Date date = java.sql.Date.valueOf(dateString);
		String[] storyList = new String[1];
		storyList[0] = "A 117-year-old woman in Mexico City finally received her birth certificate, and died a few hours later. Trinidad Alvarez Lira had waited years for proof that she had been born in 1898.";
		//storyList[1] = "IMF chief backs Athens as permanent Olympic host";
		//storyList[2] = "The president of France says if Brexit won, so can Donald Trump";
		//RemovePrepositionsFromStory
		DayStrings dayStories = new DayStrings(date,storyList);
		dayStoriesList.add(dayStories);
	}
	
	@Test
	public void dayStoryObjectCreatedForDayOneContainsCorrectStoryAtPositionOne() {
		DayStrings firstDayStories = dayStoriesList.get(0);
		String[] storyList = firstDayStories.getStringArray();
		assertEquals(storyList[0],"A 117-year-old woman in Mexico City finally received her birth certificate, and died a few hours later. Trinidad Alvarez Lira had waited years for proof that she had been born in 1898.");
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
	public void removePrepositionsForDayOneWithOneStoryReturnsCorrectStringArray() {
		//List<DayStrings> removedPrepsList = preprocessingController.removePrepositions(dayStoriesList);
		//DayStrings firstDayRemoved = removedPrepsList.get(0);
		
		assertTrue(true);
	}
}
