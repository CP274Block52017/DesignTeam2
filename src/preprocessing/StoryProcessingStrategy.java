package preprocessing;

import java.io.FileNotFoundException;
import java.util.List;

public interface StoryProcessingStrategy {
	public List<DayStrings> processStories(List<DayStrings> dayStoryList) throws FileNotFoundException;
}
