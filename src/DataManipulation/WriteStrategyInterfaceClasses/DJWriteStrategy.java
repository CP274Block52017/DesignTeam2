package DataManipulation.WriteStrategyInterfaceClasses;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;


/**
 * This class implements WriteStrategy in order to define
 * the method for writing Dow Jones opening values and their dates to the 
 * DJOpening table in our database.
 *
 */
public class DJWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into DJOpening (date, opening) values (?,?)";
		int NumConvertedDataObjects = 100; //if number is changed, please change number in ListStringArraysToDJObject
		//Uploading 8 years worth of information will take too long for JUnit tests
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=0;i<NumConvertedDataObjects;i++){
			java.sql.Date sqlDate = file.get(i).getDate();
			BigDecimal sqlDecimal = ((DJObject) file.get(i)).getOpeningValue();
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setBigDecimal(2, sqlDecimal);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
}
