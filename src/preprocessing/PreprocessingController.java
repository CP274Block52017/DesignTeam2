package preprocessing;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class PreprocessingController {
	
	public List<DayStrings> removePrepositions(List<DayStrings> dayStoryList) throws FileNotFoundException{
		PrepositionRemover prepRemover = new PrepositionRemover();
		return prepRemover.removePrepositions(dayStoryList);
	}
	
	public List<int[]> getNNList(List<DayStrings> dayStoryList) throws SQLException{
		GetWordCounts wordCounter = new GetWordCounts();
		return wordCounter.getWordCounts(dayStoryList);
	}
}
