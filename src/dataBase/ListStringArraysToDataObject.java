package dataBase;
import java.util.List;

/**
 * This interface is used to convert from CSV reader output (list of string arrays)
 * to specific writable data for the mysql database.
 *
 */

public interface ListStringArraysToDataObject {
	List<DataObject> stringtoDataObject(List<String[]> file);
}
