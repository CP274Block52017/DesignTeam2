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

public class TableController {
	private WriteStrategy tableInteractionStrategy;
	private Connection databaseConnection;
	private ReturnSetStrategy databaseReturnStrategy;
	
	public TableController(WriteStrategy databaseWriteStrategy, ReturnSetStrategy databaseReturnStrategy, String databaseAddress) throws SQLException {
		this.tableInteractionStrategy = databaseWriteStrategy;
		this.databaseConnection = DriverManager.getConnection(databaseAddress);
	}
	
	public void writeListtoDB(List<DataObject> list) throws SQLException, ParseException{
		tableInteractionStrategy.writeToTable(list,databaseConnection);
	}
	
	public List<DataObject> returnSetStrategy(ResultSet resultSet) throws SQLException {
		return databaseReturnStrategy.returnSetToDataObject(resultSet);
	}
	
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
	}
}
