package dataBase;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements ListStringArraysToDataObject to convert
 * output from the CSV reader to an object containing news stories and a date.
 * It is used to write news stories and their corresponding dates to the
 * NewsHeadlines table in the database.
 *
 */
public class ListStringArraysToNSObject implements ListStringArraysToDataObject {

	@Override
	public List<DataObject> stringtoDataObject(List<String[]> file) {
		int numOfDJtoUpload = 100; //change number in DJWriteStrategy
		//Uploading 8 years worth of information will take too long for JUnit tests
		List<DataObject> returnSet = new ArrayList<DataObject>();
		for(int i=0;i< file.size();i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			String headline = file.get(i)[1];
			DateStringObject rowObject = new DateStringObject(sqlDate,headline);
			returnSet.add(rowObject);
		}
		return returnSet;
	}

}
