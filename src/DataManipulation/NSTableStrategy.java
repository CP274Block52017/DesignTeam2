package DataManipulation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.NSObject;

public class NSTableStrategy implements TableStrategy{
	
	@Override
	public void writeToTable(List<String[]> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into NewsHeadlines (date, headline) values (?,?)";
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=1;i<76;i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setString(2, file.get(i)[1]);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
	
	@Override
	public List<DataObject> retrieveData(Connection databaseConnection) throws SQLException{
		List<DataObject> returnList = new ArrayList<DataObject>();
		Statement databaseStatement = databaseConnection.createStatement();
		String selectCommand = "select date, headline from NewsHeadlines";
		ResultSet resultSet = databaseStatement.executeQuery(selectCommand);
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			String headline = resultSet.getString("headline");
			NSObject rowObject = new NSObject(date,headline);
			returnList.add(rowObject);
		}
		return returnList;
	}
}