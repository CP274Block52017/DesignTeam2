package DataManipulation;

import java.sql.ResultSet;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

public interface ReturnSetToDataObjectStrategy {
	List<DataObject> returnSetToDataObject(ResultSet file);
}
