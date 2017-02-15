package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * This class implements WriteStrategy in order to define
 * the method for writing headlines and their dates to the 
 * NewsHeadlines table in our database.
 *
 */
public class NSWriteStrategy implements WriteStrategy {

	@Override
	public void writeToTable(List<DataObject> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into NewsHeadlines (date, headline) values (?,?)";
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=0;i< file.size();i++){
			java.sql.Date sqlDate = file.get(i).getDate();
			String headline = ((DateStringObject) file.get(i)).getString();
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setString(2, headline);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
}
