import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataBase.*;

public class DJDatabaseTest {
	private List<DataObject> DJList;
	private DatabaseController DJController;
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
		
		List<String[]> stringList = reader.readFile("Data/DJIA_table.csv");
		ListStringArraysToDJObject conversion = new ListStringArraysToDJObject();
		DJList = conversion.stringtoDataObject(stringList);
		DJWriteStrategy DJWriteStrategy = new DJWriteStrategy();
		DJReturnSetStrategy DJReturnStrategy = new DJReturnSetStrategy();
		DJController = new DatabaseController(DJWriteStrategy, DJReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);

	}
	
	@Test
	public void readWriteAllValuesCheckFirstDate() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		ResultSet returnList = DJController.retrieveDataFromDB("DJOpening", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = DJController.returnSetStrategy(returnList);
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((DJObject) dataObjectList.get(0)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckFirstOpeningValue() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		ResultSet returnList = DJController.retrieveDataFromDB("DJOpening", "2016-07-01", "2016-07-01");
		List<DataObject> dataObjectList = DJController.returnSetStrategy(returnList);
		BigDecimal correctOpening = BigDecimal.valueOf(17924.24023); 
		assertEquals(correctOpening,((DJObject) dataObjectList.get(0)).getOpeningValue());
	}
	
	@Test 
	public void readWriteAllValuesCheckLastDate() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		ResultSet returnList = DJController.retrieveDataFromDB("DJOpening", "2016-05-27", "2016-05-27");
		List<DataObject> dataObjectList = DJController.returnSetStrategy(returnList);
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-05-27");
		assertEquals(correctDate,((DJObject) dataObjectList.get(0)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckLastOpeningValue() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		ResultSet returnList = DJController.retrieveDataFromDB("DJOpening","2016-05-27" , "2016-05-27");
		List<DataObject> dataObjectList = DJController.returnSetStrategy(returnList);
		BigDecimal correctOpening = BigDecimal.valueOf(17826.84961); 
		assertEquals(correctOpening,((DJObject) dataObjectList.get(0)).getOpeningValue());
	}
	
	@After
	public void cleanUp() throws SQLException{
		//comment out deleteAll() if you want to check data in DJOpening table
		DJController.deleteAll("DJOpening");
		
	}
}
