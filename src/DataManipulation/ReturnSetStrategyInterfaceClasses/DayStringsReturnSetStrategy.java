package DataManipulation.ReturnSetStrategyInterfaceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import DataManipulation.DataObjectInterfaceClasses.DayStrings;

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
		
		List<DayStrings> returnList = new ArrayList<DayStrings>();
		for(DateStringObject i : dataList){
			java.sql.Date date = i.getDate();
			if(listDoesNotContainDate(returnList,date)){
				List<String> stringList = new ArrayList<String>();
				for(DateStringObject j : dataList){
					if(j.getDate() == date){
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
		/*NSReturnSetStrategy dataObjectConverter = new NSReturnSetStrategy();
		List<DataObject> dataList = dataObjectConverter.returnSetToDataObject(resultSet);
		/*for(int i = 0; i < dataList.size(); i++){
			System.out.println(((DateStringObject) dataList.get(i)).getString());
		}
		List<DayStrings> dayHeadlines = new ArrayList<DayStrings>(); 		
		List<java.sql.Date> dateInReturnList = new ArrayList<java.sql.Date>();
		int newsHeadlinesPerDay = 25;
		int headlineNumOnDay = 0;
		int currentHeadline = 0;
		currentHeadline += headlineNumOnDay;
		for(int i =0; i < dataList.size(); i += newsHeadlinesPerDay){
			java.sql.Date date = ((DateStringObject) dataList.get(i)).getDate();
			//System.out.println(((DateStringObject) dataList.get(i)).getString());
			if(!dateInReturnList.contains(date)){
				dateInReturnList.add(date);
				String[] dayheadlines = new String [newsHeadlinesPerDay];
				for(headlineNumOnDay = 0; headlineNumOnDay < newsHeadlinesPerDay; headlineNumOnDay++){
					//System.out.println(headlineNumOnDay);
					String headline = ((DateStringObject) dataList.get(currentHeadline + headlineNumOnDay)).getString();
					System.out.println(headline);
					dayheadlines[headlineNumOnDay] = headline; 
					if(dayheadlines.length == newsHeadlinesPerDay){
						DayStrings rowObject = new DayStrings(date, dayheadlines);
						dayHeadlines.add(rowObject);
						currentHeadline = i * newsHeadlinesPerDay;
					}
				}
			}
		}
		return dayHeadlines;*/
	}
	
	private boolean listDoesNotContainDate(List<DayStrings> dayStrings, java.sql.Date date){
		boolean returnValue = true;
		for(DayStrings i : dayStrings){
			if(i.getDate() == date){
				returnValue = false;
			}
		}
		return returnValue;
	}
}
