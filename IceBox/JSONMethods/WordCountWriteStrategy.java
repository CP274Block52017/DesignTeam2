package preprocessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import DataManipulation.WriteStrategyInterfaceClasses.WriteStrategy;

public class WordCountWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into wordCount (date, wordCount) values (?,?)";
		int NumConvertedDataObjects = 25;
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=0;i< NumConvertedDataObjects;i++){
			java.sql.Date sqlDate = file.get(i).getDate();
			String wordCount = ((DateStringObject) file.get(i)).getString();
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setString(2, wordCount);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}

}
