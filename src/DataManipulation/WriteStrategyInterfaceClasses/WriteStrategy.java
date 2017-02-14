package DataManipulation.WriteStrategyInterfaceClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

/**
 * This interface defines the method for writing data objects to their
 * corresponding tables in the database.
 *
 */
public interface WriteStrategy {
	void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException;

}
