import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataManipulation.*;
import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import DataManipulation.DataObjectInterfaceClasses.DayStrings;
import DataManipulation.ListStringArraystoDataObjectInterfaceClasses.ListStringArraysToNSObject;
import DataManipulation.ReturnSetStrategyInterfaceClasses.DayStringsReturnSetStrategy;
import DataManipulation.ReturnSetStrategyInterfaceClasses.DateStringReturnSetStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.DateStringWriteStrategy;

public class ListDayStringsTests {
	private List<DataObject> NSList;
	private DataManipulationController NSController;
	DayStringsReturnSetStrategy dateStringsReturn = new DayStringsReturnSetStrategy();
	private static boolean setUp = false;
	
	public void initialize() throws SQLException{
		MySQLInitializer initializer = new MySQLInitializer();
		initializer.setUp();
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
		CSVFileReader reader = new CSVFileReader();
		List<String[]> stringList = reader.readFile("Data/RedditNews.csv");
		ListStringArraysToNSObject conversion = new ListStringArraysToNSObject();
		NSList = conversion.stringtoDataObject(stringList);
		DateStringWriteStrategy NSWriteStrategy = new DateStringWriteStrategy();
		DateStringReturnSetStrategy NSReturnStrategy = new DateStringReturnSetStrategy();
		NSController = new DataManipulationController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
	}
	
	@Test
	public void readAllHeadlinesNewestDate() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DayStrings> testList = dateStringsReturn.returnSetToDataObject(returnList);
		
		ArrayList<String> testarray = new ArrayList<String>();
		for(DayStrings i : testList){
			String [] dayStory = i.getStringArray();
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
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-06-29", "2016-07-01");
		List<DayStrings> testList = dateStringsReturn.returnSetToDataObject(returnList);
		
		ArrayList<String> testarray = new ArrayList<String>();
		java.sql.Date wantedDate = java.sql.Date.valueOf("2016-06-29");
		for(DayStrings i : testList){
			if((i.getDate().compareTo(wantedDate)) == 0){
				String [] dayStory = i.getStringArray();
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
	
	
	@After
	public void cleanUp() throws SQLException{
		//comment out deleteAll() if you want to check data in omnipredictor tables
		NSController.deleteAll();
	}
}
