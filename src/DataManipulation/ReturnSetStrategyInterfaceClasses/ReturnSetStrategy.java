package DataManipulation.ReturnSetStrategyInterfaceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

//Conversion from Return set form the database retrieval methods to specific object types

public interface ReturnSetStrategy {
	List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException;
}
