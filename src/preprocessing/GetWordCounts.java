package preprocessing;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


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
		List<String> uniqueWordList = getUniqueWordList(dayWords);
		System.out.println(Arrays.toString(uniqueWordList.toArray()));
		WordCount[] wordCounts = new WordCount[uniqueWordList.size()];
		for(int i = 0; i < uniqueWordList.size(); i++){
			String word = uniqueWordList.get(i);
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
			if (currentWord.equals(word)) {
				count++;
			}
		}
		WordCount wordCount = new WordCount(word, count);
		return wordCount;
	}
	
	public List<String> getUniqueWordList(String[] nonUniqueWordList){
		List<String> uniqueWordList = new ArrayList<String>();
		for(int i = 0; i < nonUniqueWordList.length; i++){
			String currentWord = nonUniqueWordList[i];
			if (!uniqueWordList.contains(currentWord)) {
				uniqueWordList.add(currentWord);
			}
		}
		return uniqueWordList;
	}
	
}
