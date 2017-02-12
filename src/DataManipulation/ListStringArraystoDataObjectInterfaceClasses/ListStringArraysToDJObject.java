package DataManipulation.ListStringArraystoDataObjectInterfaceClasses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;

public class ListStringArraysToDJObject implements ListStringArraysToDataObject {
	public List<DataObject> stringtoDataObject(List<String[]> file){
		int numOfDJtoUpload = 25;
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
