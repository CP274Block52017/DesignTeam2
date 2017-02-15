package dataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List; 
	

/**
 * This class serves to write the data sets to database tables, retrieve information from the database,
 * convert retrieve information into data objects and contains a method to delete data sets from the database.
 * This implements a Strategy design pattern using a WriteStrategy interface and ReturnSetStrategy interface.
 *
 */
public class DatabaseController {
	private Connection databaseConnection;
	private WriteStrategy writeStrategy;
	private ReturnSetStrategy returnSetStrategy;
	
	public DatabaseController(WriteStrategy writeStrategy, ReturnSetStrategy returnSetStrategy, String databaseAddress) throws SQLException {
		this.writeStrategy = writeStrategy;
		this.returnSetStrategy = returnSetStrategy;
		this.databaseConnection = DriverManager.getConnection(databaseAddress);
	}
	
	public void initializeDB() throws SQLException{
		MySQLInitializer initializer = new MySQLInitializer();
		initializer.setUp();		
	}
	
	public void writeListtoDB(List<DataObject> list) throws SQLException, ParseException{
		writeStrategy.writeToTable(list,databaseConnection);
	}
	
	public ResultSet retrieveDataFromDB(String tableName, String startDate, String endDate)  throws SQLException{
		Statement databaseStatement = databaseConnection.createStatement();
		String selectCommand = "SELECT * FROM "+ tableName +" WHERE date >= \""+ startDate +"\" AND date <= \""+ endDate +"\";";
		ResultSet resultSet = databaseStatement.executeQuery(selectCommand);
		return resultSet;
	}
	
	public List<DataObject> returnSetStrategy(ResultSet resultSet) throws SQLException {
		return returnSetStrategy.returnSetToDataObject(resultSet);
	}
	
	public void deleteAll(String tableName) throws SQLException{
		System.out.println("TABLE NAME : "+tableName);
		Statement databaseStatement = databaseConnection.createStatement();
		String deleteCommand = "DELETE FROM " + tableName;
		databaseStatement.execute(deleteCommand);
	}
}
