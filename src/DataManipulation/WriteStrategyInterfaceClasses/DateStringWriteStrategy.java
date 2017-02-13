package DataManipulation.WriteStrategyInterfaceClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;

public class DateStringWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into NewsHeadlines (date, headline) values (?,?)";
		int NumConvertedDataObjects = 100; //change number in ListStringsArraysTONSObject
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
