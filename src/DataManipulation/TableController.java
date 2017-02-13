package DataManipulation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.ReturnSetStrategyInterfaceClasses.ReturnSetStrategy;
import DataManipulation.WriteStrategyInterfaceClasses.WriteStrategy; 
	
	//TableController used to run tests and for compositional design with mutiple interfaces

public class TableController {
	private WriteStrategy writeStrategy;
	private Connection databaseConnection;
	private ReturnSetStrategy returnSetStrategy;
	
	public TableController(WriteStrategy writeStrategy, ReturnSetStrategy returnSetStrategy, String databaseAddress) throws SQLException {
		this.writeStrategy = writeStrategy;
		this.returnSetStrategy = returnSetStrategy;
		this.databaseConnection = DriverManager.getConnection(databaseAddress);
	}
	
	public void writeListtoDB(List<DataObject> list) throws SQLException, ParseException{
		writeStrategy.writeToTable(list,databaseConnection);
	}
	
	public List<DataObject> returnSetStrategy(ResultSet resultSet) throws SQLException {
		return returnSetStrategy.returnSetToDataObject(resultSet);
	}
	
	//TODO new retrieve database interface for future interfaces
	public ResultSet retrieveDataFromDB(String tableName, String startDate, String endDate)  throws SQLException{
		Statement databaseStatement = databaseConnection.createStatement();
		String selectCommand = "SELECT * FROM "+ tableName +" WHERE date >= \""+ startDate +"\" AND date <= \""+ endDate +"\";";
		ResultSet resultSet = databaseStatement.executeQuery(selectCommand);
		return resultSet;
	}
	
	public void deleteAll() throws SQLException{
		Statement databaseStatement = databaseConnection.createStatement();
		String deleteCommand = "DELETE FROM DJOpening";
		databaseStatement.execute(deleteCommand);
		databaseStatement = databaseConnection.createStatement();
		deleteCommand = "DELETE FROM NewsHeadlines";
		databaseStatement.execute(deleteCommand);
		databaseStatement = databaseConnection.createStatement();
		deleteCommand = "DELETE FROM WordCount";
		databaseStatement.execute(deleteCommand);
	}
}
