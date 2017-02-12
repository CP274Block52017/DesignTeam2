package preprocessing;

import java.io.FileNotFoundException;
import java.util.List;

public class PreprocessingController {
	private StoryProcessingStrategy storyProcessingStrategy;
	
	public PreprocessingController(StoryProcessingStrategy storyProcessingStrategy){
		this.storyProcessingStrategy = storyProcessingStrategy;
	}
	
	public List<DayStrings> processStories(List<DayStrings> dayStoryList) throws FileNotFoundException{
		return storyProcessingStrategy.processStories(dayStoryList);
	}
}