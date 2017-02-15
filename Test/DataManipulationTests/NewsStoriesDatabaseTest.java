package DataManipulationTests;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

public class NewsStoriesDatabaseTest {
	private List<DataObject> NSList;
	private DatabaseController NSController;
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
		NSController = new DatabaseController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
	}
	
	@Test
	public void readWriteAllValuesCheckFirstDate() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList);
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((DateStringObject) dataObjectList.get(0)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckFirstHeadline() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList);
		String correctHeadline = "A 117-year-old woman in Mexico City finally received her birth certificate, and died a few hours later. Trinidad Alvarez Lira had waited years for proof that she had been born in 1898."; 
		assertEquals(correctHeadline,((DateStringObject) dataObjectList.get(0)).getString());
	}
	
	@Test 
	public void readWriteAllValuesCheckTwentyFiveDate() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList);
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((DateStringObject) dataObjectList.get(24)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckTwentyFiveHeadline() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList);
		String correctHeadline = "Ozone layer hole seems to be healing - US &amp; UK team shows it's shrunk &amp; may slowly recover. \"If you had to have an ozone hole anywhere in the world, it'd be Antarctica because its not teeming with life. It showed us if we didnt back off with these chemicals, wed have a crisis.\"";
		assertEquals(correctHeadline,((DateStringObject) dataObjectList.get(24)).getString());
	}
	
	
	@After
	public void cleanUp() throws SQLException{
		//comment out deleteAll() if you want to check data in NewsHeadlines table
		NSController.deleteAll("NewsHeadlines");
	}
}
