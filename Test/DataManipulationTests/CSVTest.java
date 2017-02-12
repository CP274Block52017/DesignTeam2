package DataManipulationTests;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import DataManipulation.CSVFileReader;

public class CSVTest {
	

	private List<String[]> redditNewsList;
	private List<String[]> DJList;
	
	@Before
	public void setup(){
		CSVFileReader reader = new CSVFileReader();
		redditNewsList = reader.readFile("Data/RedditNews.csv");
		DJList = reader.readFile("Data/DJIA_table.csv");
	}
	
	@Test
	public void readOneLineFromNewsCorrectDateZeroPosition() {
		String[] rowOne = redditNewsList.get(0);
		String dateOne = rowOne[0];
		assertEquals("2016-07-01", dateOne);
	}
	
	@Test
	public void readOneLineFromNewsCorrectNewsSecondPosition() {
		String[] rowOne = redditNewsList.get(0);
		String newsOne = rowOne[1];
		String correctNews = "A 117-year-old woman in Mexico City finally received her birth certificate, and died a few hours later. Trinidad Alvarez Lira had waited years for proof that she had been born in 1898.";
		assertEquals(correctNews, newsOne);
	}
	
	@Test
	public void readLineNintyNineFromNewsCorrectNewsSecondPosition() {
		String[] rowOne = redditNewsList.get(98);
		String newsOne = rowOne[1];
		String correctNews = "8 Suicide Bombers Strike Lebanon";
		assertEquals(correctNews, newsOne);
	}
	
	@Test
	public void readLineNintyNineFromNewsCorrectDateZeroPosition() {
		String[] rowOne = redditNewsList.get(98);
		String dateOne = rowOne[0];
		assertEquals("2016-06-28", dateOne);
	}
	
	@Test
	public void readDJIALineOneDate(){
		String[] rowOne = DJList.get(0);
		String date = rowOne[0];
		assertEquals("2016-07-01", date);
	}
	
	@Test
	public void readDJIALineOneOpening(){
		String[] rowOne = DJList.get(0);
		String date = rowOne[1];
		assertEquals("17924.240234", date);
	}
}
