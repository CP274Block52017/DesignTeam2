package DataManipulation.ListStringArraystoDataObjectInterfaceClasses;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.NSObject;

public class ListStringArraysToNSObject implements ListStringArraysToDataObject {

	@Override
	public List<DataObject> stringtoDataObject(List<String[]> file) {
		int numOfDJtoUpload = 25;
		List<DataObject> returnSet = null;
		for(int i=1;i< numOfDJtoUpload + 1;i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			String headline = file.get(i)[1];
			NSObject rowObject = new NSObject(sqlDate,headline);
			returnSet.add(rowObject);
		}
		return returnSet;
	}

}
