package DataManipulation.WriteStrategyInterfaceClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

// writes/uploads converted objects from ListStringArraysToDataObject classes 
//to the database and their corresponding tables

public interface WriteStrategy {
	void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException;

}
