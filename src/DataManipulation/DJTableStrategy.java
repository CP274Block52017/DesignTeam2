package DataManipulation;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;

public class DJTableStrategy implements TableStrategy{
	
	@Override
	public void writeToTable(List<String[]> file, Connection databaseConnection) throws SQLException, ParseException {
		String query = "insert into DJOpening (date, opening) values (?,?)";
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		for(int i=1;i<26;i++){
			java.sql.Date sqlDate = java.sql.Date.valueOf(file.get(i)[0]);
			BigDecimal sqlDecimal = new BigDecimal(file.get(i)[1]);
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setBigDecimal(2, sqlDecimal);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
	
	@Override
	public List<DataObject> retrieveData(Connection databaseConnection) throws SQLException{
		List<DataObject> returnList = new ArrayList<DataObject>();
		Statement databaseStatement = databaseConnection.createStatement();
		String selectCommand = "select date, opening from DJOpening";
		ResultSet resultSet = databaseStatement.executeQuery(selectCommand);
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			BigDecimal openingValue = resultSet.getBigDecimal("opening");
			DJObject rowObject = new DJObject(date,openingValue);
			returnList.add(rowObject);
		}
		return returnList;
	}
}
