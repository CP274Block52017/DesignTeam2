package preprocessing;

import java.util.List;

public interface StoryProcessingStrategy {
	public List<DayStrings> processStories(List<DayStrings> dayStoryList);
}
