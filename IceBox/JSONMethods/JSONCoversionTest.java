
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import DataManipulation.CSVFileReader;
import DataManipulation.TableController;
import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import DataManipulation.ListStringArraystoDataObjectInterfaceClasses.ListStringArraysToNSObject;
import DataManipulation.ReturnSetStrategyInterfaceClasses.NSReturnSetStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.DateStringWriteStrategy;
import preprocessing.DayWordCount;
import preprocessing.JSONCoverter;
import preprocessing.PreprocessingController;
import preprocessing.WordCount;

public class JSONCoversionTest {
	
	
	public void setup() throws SQLException, ParseException{
		List<DataObject> NSList;
	    TableController NSController;
		String localhostID = "8889";
		String username = "root";
		String password = "root";
		CSVFileReader reader = new CSVFileReader();
		List<String[]> stringList = reader.readFile("Data/RedditNews.csv");
		PreprocessingController controller = new PreprocessingController();
		ListStringArraysToNSObject conversion = new ListStringArraysToNSObject();
		NSList = conversion.stringtoDataObject(stringList);
		DateStringWriteStrategy NSWriteStrategy = new DateStringWriteStrategy();
		NSReturnSetStrategy NSReturnStrategy = new NSReturnSetStrategy();
		NSController = new TableController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList);
		List<DateStringObject> dataStringList = (List<DateStringObject>) dataObjectList;
	}
	@Test
	public void test() {
		JSONCoverter conversion = new JSONCoverter();
		List<DayWordCount> file = new ArrayList<DayWordCount>();
		java.sql.Date date = java.sql.Date.valueOf("2016-01-07");

		for(int t = 0; t < 3; t++){
			WordCount[] wordcountList = new WordCount[5];
			for(int i = 0; i < wordcountList.length; i++){
				
				WordCount wordCount = new WordCount("tester" + i+t, i);
				wordcountList[i] = wordCount; 
			}
			DayWordCount dateWordCount = new DayWordCount(date, wordcountList);
			file.add(dateWordCount);
		}
		
		List<DataObject> convertedList = new ArrayList<DataObject>();
		convertedList = conversion.getJSON(file);
		for(int i = 0; i < convertedList.size(); i++){
			date = convertedList.get(i).getDate();
			String wordListday = ((DateStringObject) convertedList.get(i)).getString();
			System.out.println(date + ", " +wordListday);
		}
		assertTrue(true);
	}

}
