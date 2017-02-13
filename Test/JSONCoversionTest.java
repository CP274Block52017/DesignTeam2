import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import preprocessing.DayWordCounts;
import preprocessing.JSONCoverter;
import preprocessing.WordCount;

public class JSONCoversionTest {

	@Test
	public void test() {
		JSONCoverter conversion = new JSONCoverter();
		List<DayWordCounts> file = new ArrayList<DayWordCounts>();
		java.sql.Date date = java.sql.Date.valueOf("2016-01-07");

		for(int t = 0; t < 3; t++){
			WordCount[] wordcountList = new WordCount[5];
			for(int i = 0; i < wordcountList.length; i++){
				
				WordCount wordCount = new WordCount("tester" + i+t, i);
				wordcountList[i] = wordCount; 
			}
			DayWordCounts dateWordCount = new DayWordCounts(date, wordcountList);
			file.add(dateWordCount);
		}
		
		List<DataObject> convertedList = new ArrayList<DataObject>();
		//convertedList = conversion.getJSON(file);
		for(int i = 0; i < convertedList.size(); i++){
			date = convertedList.get(i).getDate();
			String wordListday = ((DateStringObject) convertedList.get(i)).getString();
			System.out.println(date + ", " +wordListday);
		}
		assertTrue(true);
	}

}
