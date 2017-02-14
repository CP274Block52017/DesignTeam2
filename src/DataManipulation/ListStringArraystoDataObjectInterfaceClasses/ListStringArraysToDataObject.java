package DataManipulation.ListStringArraystoDataObjectInterfaceClasses;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

//Conversion from the CSVreader's output to List<DataObject> (objects to retrieve dates and other types)

public interface ListStringArraysToDataObject {
	List<DataObject> stringtoDataObject(List<String[]> file);
}
