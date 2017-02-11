package DataManipulation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject; 

public class TableController {
	private TableStrategy tableInteractionStrategy;
	private Connection databaseConnection;
	
	public TableController(String databaseAddress, TableStrategy databaseWriteStrategy) throws SQLException {
		this.tableInteractionStrategy = databaseWriteStrategy;
		this.databaseConnection = DriverManager.getConnection(databaseAddress);
	}
	
	public void writeListtoDB(List<String[]> list) throws SQLException, ParseException{
		tableInteractionStrategy.writeToTable(list,databaseConnection);
	}
	
	public List<DataObject> retrieveDataFromDB() throws SQLException{
		return tableInteractionStrategy.retrieveData(databaseConnection);
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
