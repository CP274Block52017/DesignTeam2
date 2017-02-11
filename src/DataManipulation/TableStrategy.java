package DataManipulation;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

public interface TableStrategy {
	void writeToTable(List<String[]> file, Connection databaseConnection) throws SQLException, ParseException;
	List<DataObject> retrieveData(Connection databaseConnection)  throws SQLException;
}
