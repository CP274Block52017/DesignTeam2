package preprocessing;

import java.util.List;

public class PreprocessingController {
	private StoryProcessingStrategy storyProcessingStrategy;
	
	public PreprocessingController(StoryProcessingStrategy storyProcessingStrategy){
		this.storyProcessingStrategy = storyProcessingStrategy;
	}
	
	public List<DayStrings> processStories(List<DayStrings> dayStoryList){
		return storyProcessingStrategy.processStories(dayStoryList);
	}
	
	public List<DayWordCount> getWordCounts(List<DayStrings> dayStoryList){
		GetWordCounts wordCounter = new GetWordCounts();
		return wordCounter.getWordCounts(dayStoryList);
	}
}
