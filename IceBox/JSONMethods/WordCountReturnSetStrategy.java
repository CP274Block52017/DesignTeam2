package preprocessing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;
import DataManipulation.ReturnSetStrategyInterfaceClasses.ReturnSetStrategy;

public class WordCountReturnSetStrategy implements ReturnSetStrategy {

	@Override
	public List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException {
		List<DataObject> returnList = new ArrayList<DataObject>();
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			String wordCount = resultSet.getString("wordCount");
			DateStringObject rowObject = new DateStringObject(date,wordCount);
			returnList.add(rowObject);
		}
		return returnList;
	}
}
