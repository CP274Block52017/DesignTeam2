package preprocessing;
import java.io.FileNotFoundException;
import java.util.List;

public class PreprocessingController {
	
	public List<DayStrings> removePrepositions(List<DayStrings> dayStoryList) throws FileNotFoundException{
		PrepositionRemover prepRemover = new PrepositionRemover();
		return prepRemover.removePrepositions(dayStoryList);
	}
	
	public List<int[]> getWordCounts(List<DayStrings> dayStoryList){
		GetWordCounts wordCounter = new GetWordCounts();
		return wordCounter.getWordCounts(dayStoryList);
	}
}
