package DataManipulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.NSObject;

public class NSWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into NewsHeadlines (date, headline) values (?,?)";
		int NumConvertedDataObjects = 25;
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=1;i< NumConvertedDataObjects + 1;i++){
			java.sql.Date sqlDate = file.get(i).getDate();
			String headline = ((NSObject) file.get(i)).getHeadline();
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setString(2, headline);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
}
