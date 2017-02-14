package preprocessing;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DayStrings;

/**
 * This class controls the flow of the preprocessing from removing prepositions to returning a list of 
 * integer arrays to be passed to the neural network. 
 */

public class PreprocessingController {
	
	public List<DayStrings> removePrepositions(List<DayStrings> dayStoryList) throws FileNotFoundException{
		PrepositionRemover prepRemover = new PrepositionRemover();
		return prepRemover.removePrepositions(dayStoryList);
	}
	
	public List<int[]> getNNList(List<DayStrings> dayStoryList) throws SQLException, ParseException{
		WordCountMaker wordCounter = new WordCountMaker();
		return wordCounter.getWordCounts(dayStoryList);
	}
}
