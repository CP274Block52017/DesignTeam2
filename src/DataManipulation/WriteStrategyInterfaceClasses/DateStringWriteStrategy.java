package DataManipulation.WriteStrategyInterfaceClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;

/**
 * This class implements WriteStrategy in order to define
 * the method for writing headlines and their dates to the 
 * NewsHeadlines table in our database.
 *
 */
public class DateStringWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into NewsHeadlines (date, headline) values (?,?)";
		int NumConvertedDataObjects = 100; //if number is changed, also change number in ListStringsArraysTONSObject
		//Uploading 8 years worth of information will take too long for JUnit tests
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=0;i< NumConvertedDataObjects;i++){
			java.sql.Date sqlDate = file.get(i).getDate();
			String headline = ((DateStringObject) file.get(i)).getString();
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setString(2, headline);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
}
