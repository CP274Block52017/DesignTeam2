package DataManipulationTests;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataManipulation.*;
import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import DataManipulation.ListStringArraystoDataObjectInterfaceClasses.ListStringArraysToDJObject;
import DataManipulation.ListStringArraystoDataObjectInterfaceClasses.ListStringArraysToNSObject;
import DataManipulation.ReturnSetStrategyInterfaceClasses.DJReturnSetStrategy;
import DataManipulation.ReturnSetStrategyInterfaceClasses.NSReturnSetStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.DJWriteStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.DateStringWriteStrategy;

public class ReadDateRangeFromDBTest {
	private List<DataObject> DJList;
	private TableController DJController;
	private List<DataObject> NSList;
	private TableController NSController;
	private static boolean setUp = false;
	
	CSVFileReader reader = new CSVFileReader();
	private List<String[]> DJStringList = reader.readFile("Data/DJIA_table.csv");
	private List<String[]> NSStringList = reader.readFile("Data/RedditNews.csv");


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

		ListStringArraysToDJObject DJconversion = new ListStringArraysToDJObject();
		DJList = DJconversion.stringtoDataObject(DJStringList);
		DJWriteStrategy DJWriteStrategy = new DJWriteStrategy();
		DJReturnSetStrategy DJReturnStrategy = new DJReturnSetStrategy();
		DJController = new TableController(DJWriteStrategy, DJReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
	
		ListStringArraysToNSObject NSconversion = new ListStringArraysToNSObject();
		NSList = NSconversion.stringtoDataObject(NSStringList);
		DateStringWriteStrategy NSWriteStrategy = new DateStringWriteStrategy();
		NSReturnSetStrategy NSReturnStrategy = new NSReturnSetStrategy();
		NSController = new TableController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);

	}
	
	@Test
	public void readFromLastThreeDaysReturnsOpeningCorrectValues() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		ResultSet returnList = DJController.retrieveDataFromDB("DJOpening","2016-06-29" , "2016-07-01");
		List<DataObject> dataObjectList = DJController.returnSetStrategy(returnList);

		BigDecimal[] testList =  new BigDecimal[dataObjectList.size()];
		for(int i = 0; i < dataObjectList.size(); i++){
			BigDecimal openingValue = ((DJObject)dataObjectList.get(i)).getOpeningValue();
			testList[i] = openingValue;
		}
		BigDecimal[] correctList =  new BigDecimal[dataObjectList.size()];
		for(int i = 0; i < dataObjectList.size(); i++){
			BigDecimal openingValue = ((DJObject)DJList.get(i)).getOpeningValue();
			openingValue = openingValue.setScale(5, BigDecimal.ROUND_HALF_UP);
			correctList[i] = openingValue;
		}
		assertArrayEquals(correctList,testList);
	}
	
	@Test
	public void readFromLastThreeDaysReturnsHeadlines() throws SQLException, ParseException {
		NSController.writeListtoDB(NSList);
		ResultSet returnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-06-29" , "2016-07-01");
		List<DataObject> dataObjectList = NSController.returnSetStrategy(returnList);
		
		String[] testList =  new String[dataObjectList.size()];
		for(int i = 0; i < dataObjectList.size(); i++){
			String headline = ((DateStringObject)dataObjectList.get(i)).getString();
			testList[i] = headline;
		}
		
		String[] correctList =  new String[dataObjectList.size()];
		for(int i = 0; i < dataObjectList.size(); i++){
			String headline = ((DateStringObject)NSList.get(i)).getString();
			correctList[i] = headline;
		}
		assertArrayEquals(correctList,testList);
	}
	
	@After
	public void cleanUp() throws SQLException{
		//comment out deleteAll() if you want to check data in omnipredictor tables
		//DJController.deleteAll();
	}
}
