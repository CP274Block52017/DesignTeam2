package DataManipulation.ReturnSetToDataObjectStrategyInterfaceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

public interface ReturnSetToDataObjectStrategy {
	List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException;
}
