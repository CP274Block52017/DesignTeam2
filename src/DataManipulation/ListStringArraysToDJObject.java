package DataManipulation;

import java.math.BigDecimal;
import java.util.List;

public class ListStringArraysToDJObject implements ListStringArraysToDataObject {
	public List<DataObject> stringtoDataObject(List<String[]> file){
		int numOfDJtoUpload = 25;
		List<DataObject> returnSet = null;
		for(int i=1;i< numOfDJtoUpload + 1;i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			BigDecimal sqlDecimal = new BigDecimal(file.get(i)[1]);
			DJObject rowObject = new DJObject(sqlDate,sqlDecimal);
			returnSet.add(rowObject);
		}
		return returnSet;
	}

}
