package preprocessing;

import java.util.List;

import dataBase.DJObject;
import dataBase.DataObject;
import dataBase.DayStrings;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * This class takes a list of DayStrings output by the PrepositionRemover and converts it into a 
 * list of integer arrays each containing Dow Jones opening value, followed by three integers
 * representing a date, followed by a word count for each unique word from all days in the set.
 * These integer arrays are sent to the neural network as training sets.
 */

public class WordCountMaker {
	//Get unique word list for all days and check counts of each unique word for each day
	public List<int[]> getWordCounts(List<DayStrings> dayWordList, List<DataObject> dateDJList) throws SQLException, ParseException{
		List<String> uniqueWordsAllDays = getUniqueWordList(dayWordList);
		return getWordCountList(dayWordList, uniqueWordsAllDays, dateDJList);
	}
	
	//Get counts of each unique word for each day
	private List<int[]> getWordCountList(List<DayStrings> dayWordList, List<String> uniqueWordsAllDays, List<DataObject> dateDJList) throws SQLException, ParseException{
		List<int[]> returnList = new ArrayList<int[]>();
		for(DayStrings i : dayWordList){
			java.sql.Date DJDate = null;
			BigDecimal dowValue = null;
			for(DataObject j: dateDJList){
				if(i.getDate().compareTo(j.getDate()) == 0){
					 DJDate = j.getDate();
					 dowValue = ((DJObject)j).getOpeningValue(); 
				}
			}
			int[] NNSingleArray = getNNSingleArray(DJDate, dowValue, i, uniqueWordsAllDays);
			returnList.add(NNSingleArray);
		}
		return returnList;
	}
	//Get an int array formatted correctly for the neural network for each day
	private int[] getNNSingleArray(java.sql.Date DJDate, BigDecimal dowValue, DayStrings i, List<String> uniqueWordsAllDays) throws SQLException, ParseException{
		int headerSize = 4;
		int[] NNSingleArray = getHeaderInfo(DJDate, dowValue, uniqueWordsAllDays.size(), headerSize);
		String[] wordList = i.getStringArray();
		for(int j = 0;j<uniqueWordsAllDays.size();j++){
			NNSingleArray[j+headerSize] = getCount(wordList,uniqueWordsAllDays.get(j));
		}
		return NNSingleArray;
	}
	
	//Add header info to int array for neural network
	private int[] getHeaderInfo(java.sql.Date DJDate, BigDecimal dowValue, int uniqueWordsSize, int headerSize) throws SQLException, ParseException{
		int[] NNSingleArray = new int[uniqueWordsSize+headerSize];
		NNSingleArray[0] = dowValue.intValue();
		java.sql.Date date = DJDate;
		LocalDate localDate = date.toLocalDate();
		NNSingleArray[1] = localDate.getDayOfMonth();
		NNSingleArray[2] = localDate.getMonthValue();
		NNSingleArray[3] = localDate.getYear();
		return NNSingleArray;
	}
	
	
	//Get instances of a word in a wordlist
	private int getCount(String[] wordList, String word){
		int count = 0;
		for(String i:wordList){
			if(i.equals(word)){
				count++;
			}
		}
		return count;
	}
	
	//Create unique word list from a non-unique word list
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
