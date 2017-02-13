package DataManipulation.ListStringArraystoDataObjectInterfaceClasses;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;

public class ListStringArraysToNSObject implements ListStringArraysToDataObject {

	@Override
	public List<DataObject> stringtoDataObject(List<String[]> file) {
		int numOfDJtoUpload = 100; //change number in DJWriteStrategy
		//Uploading 8 years worth of information will take too long for JUnit tests
		List<DataObject> returnSet = new ArrayList<DataObject>();
		for(int i=0;i< numOfDJtoUpload;i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			String headline = file.get(i)[1];
			DateStringObject rowObject = new DateStringObject(sqlDate,headline);
			returnSet.add(rowObject);
		}
		return returnSet;
	}

}
