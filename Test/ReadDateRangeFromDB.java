import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataManipulation.*;
import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.NSObject;

public class ReadDateRangeFromDB {
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
		NSController.writeListtoDB(NSList);
	}
	
	@Test
	public void readFromLastTwoDaysReturnsCorrectValues() throws SQLException, ParseException {
		List<DataObject> returnList = NSController.retrieveDataFromDB("NewsHeadlines", );
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((NSObject) returnList.get(0)).getDate());

	}
	
	@After
	public void cleanUp() throws SQLException{
		//comment out deleteAll() if you want to check data in omnipredictor tables
		//NSController.deleteAll();
	}
}
