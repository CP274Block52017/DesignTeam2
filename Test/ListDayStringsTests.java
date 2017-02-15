import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataBase.*;

public class ListDayStringsTests {
	private List<DataObject> NSList;
	private DatabaseController NSController;
	DayStringsReturnSetStrategy dateStringsReturn = new DayStringsReturnSetStrategy();
	private static boolean setUp = false;
	
	public void initialize() throws SQLException, ParseException{
		MySQLInitializer initializer = new MySQLInitializer();
		initializer.setUpDatabase();
	}
	
	@Before
	public void setup() throws SQLException, ParseException{
		if(!setUp){
			initialize();
			setUp = true;
		}
		//localhostID, username, and password are set to default MySQL
		String localhostID = "8889";
		String username = "root";
		String password = "root";
		NSWriteStrategy NSWriteStrategy = new NSWriteStrategy();
		DateStringReturnSetStrategy NSReturnStrategy = new DateStringReturnSetStrategy();
		NSController = new DatabaseController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
	}
	
	@Test
	public void readAllHeadlinesNewestDate() throws SQLException, ParseException {
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DataObject> testList = dateStringsReturn.returnSetToDataObject(returnList);
		
		ArrayList<String> testarray = new ArrayList<String>();
		for(DataObject i : testList){
			String [] dayStory = ((DayStrings) i).getStringArray();
			for(String j: dayStory){
				testarray.add(j);
			}
		}
		
		ResultSet returnList2 = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		String[] test = testarray.toArray(new String [testarray.size()]);
		ArrayList<String> correctarray = new ArrayList<String>();
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList2);
		for(DataObject i: dataObjectList){
			String headlines = ((DateStringObject) i).getString();
			correctarray.add(headlines);
		}
		String[] correct = correctarray.toArray(new String [correctarray.size()]);
		assertArrayEquals(correct, test);
	}
	
	@Test
	public void read3HeadlinesDayCompare1Day() throws SQLException, ParseException {
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-06-29", "2016-07-01");
		List<DataObject> testList = dateStringsReturn.returnSetToDataObject(returnList);
		
		ArrayList<String> testarray = new ArrayList<String>();
		java.sql.Date wantedDate = java.sql.Date.valueOf("2016-06-29");
		for(DataObject i : testList){
			if((i.getDate().compareTo(wantedDate)) == 0){
				String [] dayStory = ((DayStrings) i).getStringArray();
				for(String j: dayStory){
					testarray.add(j);
				}
			}
		}
		String[] test = testarray.toArray(new String [testarray.size()]);

		ResultSet returnList2 = NSController.retrieveDataFromDB("NewsHeadlines", "2016-06-29", "2016-06-29");
		ArrayList<String> correctarray = new ArrayList<String>();
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList2);
		for(DataObject i: dataObjectList){
			String headlines = ((DateStringObject) i).getString();
			correctarray.add(headlines);
		}
		String[] correct = correctarray.toArray(new String [correctarray.size()]);
		assertArrayEquals(correct, test);
	}
}
