package preprocessing;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class PreprocessingController {
	
	//Remove prepositions from news stories
	public List<DayStrings> removePrepositions(List<DayStrings> dayStoryList) throws FileNotFoundException{
		PrepositionRemover prepRemover = new PrepositionRemover();
		return prepRemover.removePrepositions(dayStoryList);
	}
	
	//Get list of integer arrays for each day 
	//Each integer array corresponds to one day and contains
	//the counts for each unique word
	public List<int[]> getNNList(List<DayStrings> dayStoryList) throws SQLException, ParseException{
		WordCountMaker wordCounter = new WordCountMaker();
		return wordCounter.getWordCounts(dayStoryList);
	}
}
