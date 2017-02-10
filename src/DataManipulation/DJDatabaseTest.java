package DataManipulation;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DJDatabaseTest {
	private List<String[]> DJList;
	private TableController DJController;
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
		DJList = reader.readFile("CSV_Data/DJIA_table.csv");
		DJTableStrategy DJTableStrategy = new DJTableStrategy();
		DJController = new TableController("jdbc:mysql://localhost:8889/omnipredictor?user=root&password=root",DJTableStrategy);
	}
	
	@Test
	public void readWriteAllValuesCheckFirstDate() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		List<DataObject> returnList = DJController.retrieveDataFromDB();
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-07-01");
		assertEquals(correctDate,((DJObject) returnList.get(0)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckFirstOpeningValue() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		List<DataObject> returnList = DJController.retrieveDataFromDB();
		BigDecimal correctOpening = BigDecimal.valueOf(17924.24023); 
		assertEquals(correctOpening,((DJObject) returnList.get(0)).getOpeningValue());
	}
	
	@Test 
	public void readWriteAllValuesCheckLastDate() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		List<DataObject> returnList = DJController.retrieveDataFromDB();
		java.sql.Date correctDate = java.sql.Date.valueOf("2016-05-27");
		assertEquals(correctDate,((DJObject) returnList.get(returnList.size() -1)).getDate());
	}
	
	@Test 
	public void readWriteAllValuesCheckLastOpeningValue() throws SQLException, ParseException {
		DJController.writeListtoDB(DJList);
		List<DataObject> returnList = DJController.retrieveDataFromDB();
		BigDecimal correctOpening = BigDecimal.valueOf(17826.84961); 
		assertEquals(correctOpening,((DJObject) returnList.get(returnList.size() -1)).getOpeningValue());
	}
	
	@After
	public void cleanUp() throws SQLException{
		DJController.deleteAll();
		
	}
}
