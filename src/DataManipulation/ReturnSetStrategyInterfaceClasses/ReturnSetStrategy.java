package DataManipulation.ReturnSetStrategyInterfaceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;


public interface ReturnSetStrategy {
	List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException;
}
