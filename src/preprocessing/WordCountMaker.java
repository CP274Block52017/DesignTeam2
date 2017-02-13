package preprocessing;

import java.util.List;

import DataManipulation.TableController;
import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.ReturnSetStrategyInterfaceClasses.DJReturnSetStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.DJWriteStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.DateStringWriteStrategy;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class GetWordCounts {
	public List<int[]> getWordCounts(List<DayStrings> dayWordList) throws SQLException{
		List<String> uniqueWordsAllDays = getUniqueWordList(dayWordList);
		return getWordCountList(dayWordList, uniqueWordsAllDays);
	}
	
	private List<int[]> getWordCountList(List<DayStrings> dayWordList, List<String> uniqueWordsAllDays) throws SQLException{
		List<int[]> returnList = new ArrayList<int[]>();
		for(DayStrings i : dayWordList){
			int[] NNSingleArray = getNNSingleArray(i,uniqueWordsAllDays);
			returnList.add(NNSingleArray);
		}
		return returnList;
	}
	
	private int[] getNNSingleArray(DayStrings i, List<String> uniqueWordsAllDays) throws SQLException{
		int headerSize = 4;
		int[] NNSingleArray = getHeaderInfo(i, uniqueWordsAllDays.size(), headerSize);
		String[] wordList = i.getStringArray();
		for(int j = 0;j<uniqueWordsAllDays.size();j++){
			NNSingleArray[j+headerSize] = getCount(wordList,uniqueWordsAllDays.get(j));
		}
		return NNSingleArray;
	}
	
	private int[] getHeaderInfo(DayStrings i, int uniqueWordsSize, int headerSize) throws SQLException{
		int[] NNSingleArray = new int[uniqueWordsSize+headerSize];
		NNSingleArray[0] = getDOW(i.getDate().toString());
		java.sql.Date date = i.getDate();
		LocalDate localDate = date.toLocalDate();
		NNSingleArray[1] = localDate.getDayOfMonth();
		NNSingleArray[2] = localDate.getMonthValue();
		NNSingleArray[3] = localDate.getYear();
		return NNSingleArray;
	}
	
	private int getDOW(String date) throws SQLException{
		String localhostID = "8889";
		String username = "root";
		String password = "root";
		DJWriteStrategy dJWriteStrategy = new DJWriteStrategy();
		DJReturnSetStrategy dJReturnSetStrategy = new DJReturnSetStrategy();
		TableController dJController = new TableController(dJWriteStrategy, dJReturnSetStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
		ResultSet returnList = dJController.retrieveDataFromDB("DJOpening", date, date.toString());
		List<DataObject> dataObjectList = dJController.returnSetStrategy(returnList);
		BigDecimal dowValue = ((DJObject)dataObjectList).getOpeningValue();
		return dowValue.intValue();
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