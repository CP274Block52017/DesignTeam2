package preprocessing;

import java.util.List;
import java.util.ArrayList;


public class GetWordCounts {
	
	public List<DayWordCount> getWordCounts(List<DayStrings> dayStoryList){
		List<DayWordCount> dayWordCounts = new ArrayList<DayWordCount>();
		for(int i = 0; i < dayStoryList.size(); i++) {
			DayStrings dayStories = dayStoryList.get(i);
			DayWordCount dayWordCount = getDayWordCounts(dayStories);
			dayWordCounts.add(dayWordCount);
		}
		return dayWordCounts;
	}
	
	public DayWordCount getDayWordCounts(DayStrings dayStories){
		String[] dayWords = dayStories.getStringArray();
		WordCount[] wordCounts = new WordCount[dayWords.length];
		for(int i = 0; i < dayWords.length; i++){
			String word = dayWords[i];
			WordCount wordCount = getWordCount(word, dayWords);
			wordCounts[i] = wordCount;
		}
		DayWordCount dayWordCount = new DayWordCount(dayStories.getDate(), wordCounts);
		return dayWordCount;
	}
	
	public WordCount getWordCount(String word, String[] dayWords){
		int count = 0;
		for(int i = 0; i < dayWords.length; i++){
			String currentWord = dayWords[i];
			if (currentWord == word) {
				count++;
			}
		}
		WordCount wordCount = new WordCount(word, count);
		return wordCount;
	}
	
}
