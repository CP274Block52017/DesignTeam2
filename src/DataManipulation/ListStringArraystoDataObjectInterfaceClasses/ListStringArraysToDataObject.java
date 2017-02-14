package DataManipulation.ListStringArraystoDataObjectInterfaceClasses;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;

/**
 * This interface is used to convert from CSV reader output (list of string arrays)
 * to specific writable data for the mysql database.
 *a
 */
public interface ListStringArraysToDataObject {
	List<DataObject> stringtoDataObject(List<String[]> file);
}
