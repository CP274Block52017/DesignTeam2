package DataManipulation;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

public interface WriteStrategy {
	void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException;

}
