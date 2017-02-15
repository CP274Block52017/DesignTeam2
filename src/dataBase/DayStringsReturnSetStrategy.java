package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to convert data sets retrieved from the database
 * into DayStringsObjects that can be used by the preprocessor to create
 * input values for the neural network.
 *
 */
public class DayStringsReturnSetStrategy {

	public List<DayStrings> returnSetToDataObject(ResultSet resultSet) throws SQLException {
		List<DateStringObject> dataList = new ArrayList<DateStringObject>();
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			String headline = resultSet.getString("headline");
			DateStringObject rowObject = new DateStringObject(date,headline);
			dataList.add(rowObject);
		}
		/*System.out.println(dataList.size());
		for(DateStringObject i:dataList){
			System.out.println(i.getDate());
			System.out.println(i.getString());
		}*/
		List<DayStrings> returnList = new ArrayList<DayStrings>();
		for(DateStringObject i : dataList){
			java.sql.Date date = i.getDate();
			if(listDoesNotContainDate(returnList,date)){
				List<String> stringList = new ArrayList<String>();
				for(DateStringObject j : dataList){
					if(j.getDate().compareTo(date)==0){
						stringList.add(j.getString());
					}
				}
				String[] storyArray = new String[stringList.size()];
				storyArray = stringList.toArray(storyArray);
				DayStrings newDayStrings = new DayStrings(date,storyArray);
				returnList.add(newDayStrings);
			}
		}
		return returnList;
	}
	
	private boolean listDoesNotContainDate(List<DayStrings> dayStrings, java.sql.Date date){
		boolean returnValue = true;
		for(DayStrings i : dayStrings){
			if(i.getDate().compareTo(date)==0){
				returnValue = false;
			}
		}
		return returnValue;
	}
}
