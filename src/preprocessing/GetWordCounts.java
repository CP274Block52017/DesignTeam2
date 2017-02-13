package preprocessing;

import java.util.List;
import java.util.ArrayList;


public class GetWordCounts {
	
	public List<int[]> getWordCounts(List<DayStrings> dayWordList){
		List<String> uniqueWordsAllDays = getUniqueWordList(dayWordList);
		return getWordCountList(dayWordList, uniqueWordsAllDays);
	}
	
	private List<int[]> getWordCountList(List<DayStrings> dayWordList, List<String> uniqueWordsAllDays){
		List<int[]> returnList = new ArrayList<int[]>();
		for(DayStrings i : dayWordList){
			int[] wordCounts = getWordCounts(i,uniqueWordsAllDays);
			returnList.add(wordCounts);
		}
		return returnList;
	}
	
	private int[] getWordCounts(DayStrings i, List<String> uniqueWordsAllDays){
		int[] wordCounts = new int[uniqueWordsAllDays.size()];
		String[] wordList = i.getStringArray();
		for(int j = 0;j<uniqueWordsAllDays.size();j++){
			wordCounts[j] = getCount(wordList,uniqueWordsAllDays.get(j));
		}
		return wordCounts;
	}
	
	private int getCount(String[] wordList, String word){
		int count = 0;
		for(String i:wordList){
			if(i.equals(word)){
				count++;
			}
		}
		return count;
	}
	
	private List<String> getUniqueWordList(List<DayStrings> dayWordList){
		List<String> uniqueWordList = new ArrayList<String>();
		for(DayStrings i : dayWordList){
			String[] wordList = i.getStringArray();
			for(String j : wordList){
				if(!uniqueWordList.contains(j)){
					uniqueWordList.add(j);
				}
			}
		}
		return uniqueWordList;
	}
	
}
