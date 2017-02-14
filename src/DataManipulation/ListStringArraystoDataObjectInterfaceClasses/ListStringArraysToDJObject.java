package DataManipulation.ListStringArraystoDataObjectInterfaceClasses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;

/**
 * This class implements ListStringArraysToDataObject to convert
 * output from the CSV reader to an object containing a Dow Jones value and date.
 * It is used to write Dow Jones values and their corresponding dates to the
 * DJOpening table in the database.
 *
 */
public class ListStringArraysToDJObject implements ListStringArraysToDataObject {
	public List<DataObject> stringtoDataObject(List<String[]> file){
		int numOfDJtoUpload = 100;//if number is changed, please change number in DJWriteStrategy
		//Uploading 8 years worth of information will take too long for JUnit tests
		List<DataObject> returnSet = new ArrayList<DataObject>();
		for(int i=0;i< numOfDJtoUpload;i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			BigDecimal sqlDecimal = new BigDecimal(file.get(i)[1]);
			DJObject rowObject = new DJObject(sqlDate,sqlDecimal);
			returnSet.add(rowObject);
		}
		return returnSet;
	}
}
