package DataManipulation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.WriteStrategyInterfaceClasses.WriteStrategy; 

public class TableController {
	private WriteStrategy tableInteractionStrategy;
	private Connection databaseConnection;
	
	public TableController(String databaseAddress, WriteStrategy databaseWriteStrategy) throws SQLException {
		this.tableInteractionStrategy = databaseWriteStrategy;
		this.databaseConnection = DriverManager.getConnection(databaseAddress);
	}
	
	public void writeListtoDB(List<DataObject> list) throws SQLException, ParseException{
		tableInteractionStrategy.writeToTable(list,databaseConnection);
	}
	
	ResultSet retrieveData(Connection databaseConnection, String tableName, java.sql.Date startDate, java.sql.Date endDate)  throws SQLException{
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
