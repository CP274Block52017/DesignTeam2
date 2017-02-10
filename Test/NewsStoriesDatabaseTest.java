import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataManipulation.*;

public class NewsStoriesDatabaseTest {
	private List<String[]> NSList;
	private TableController NSController;
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
		CSVFileReader reader = new CSVFileReader();
		//localhostID, username, and password are set to default MySQL
		String localhostID = "8889";
		String username = "root";
		String password = "root";
		NSList = reader.readFile("Data/RedditNews.csv");
		NSTableStrategy NSTableStrategy = new NSTableStrategy();
		NSController = new TableController("jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password,NSTableStrategy);
	}
	
	@Test
	public void readWriteAllValuesCheckFirstDate() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		List<DataObject> returnList = NSController.retrieveDataFromDB();
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((NSObject) returnList.get(0)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckFirstOpeningValue() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		List<DataObject> returnList = NSController.retrieveDataFromDB();
		String correctHeadline = "A 117-year-old woman in Mexico City finally received her birth certificate, and died a few hours later. Trinidad Alvarez Lira had waited years for proof that she had been born in 1898."; 
		assertEquals(correctHeadline,((NSObject) returnList.get(0)).getHeadline());
	}
	
	@Test 
	public void readWriteAllValuesCheckLastDate() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		List<DataObject> returnList = NSController.retrieveDataFromDB();
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((NSObject) returnList.get(returnList.size() -1)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckLastOpeningValue() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		List<DataObject> returnList = NSController.retrieveDataFromDB();
		String correctHeadline = "Ozone layer hole seems to be healing - US &amp; UK team shows it's shrunk &amp; may slowly recover. \"If you had to have an ozone hole anywhere in the world, it'd be Antarctica because its not teeming with life. It showed us if we didnt back off with these chemicals, wed have a crisis.\"";
		assertEquals(correctHeadline,((NSObject) returnList.get(returnList.size() -1)).getHeadline());
	}
	
	@After
	public void cleanUp() throws SQLException{
		//comment out deleteAll() if you want to check data in table
		NSController.deleteAll();
		
	}
}
