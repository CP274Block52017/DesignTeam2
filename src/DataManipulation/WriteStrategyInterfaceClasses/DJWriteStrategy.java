package DataManipulation.WriteStrategyInterfaceClasses;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;

public class DJWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into DJOpening (date, opening) values (?,?)";
		int NumConvertedDataObjects = 25;
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
