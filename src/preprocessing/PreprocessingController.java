package preprocessing;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import dataBase.DJObject;

import dataBase.DataObject;
import dataBase.DayStrings;
import preprocessing.PrepositionRemover;
import preprocessing.WordCountMaker;

/**
 * This class controls the flow of the preprocessing from removing prepositions to returning a list of 
 * integer arrays to be passed to the neural network. 
 */

public final class PreprocessingController {
	
	public List<DayStrings> removePrepositions(List<DataObject> nsDataObjectList) throws FileNotFoundException{
		PrepositionRemover prepRemover = new PrepositionRemover();
		return prepRemover.removePrepositions(nsDataObjectList);
	}
	
	public List<int[]> getNNList(List<DayStrings> dayStoryList, List<DataObject> dateDJList) throws SQLException, ParseException{
		WordCountMaker wordCounter = new WordCountMaker();
		return wordCounter.getWordCounts(dayStoryList, dateDJList);
	}
}
