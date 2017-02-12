package DataManipulation.ReturnSetStrategyInterfaceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.NSObject;

public class NSReturnSetStrategy implements ReturnSetStrategy {

	@Override
	public List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException {
		List<DataObject> returnList = new ArrayList<DataObject>();
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			String headline = resultSet.getString("headline");
			NSObject rowObject = new NSObject(date,headline);
			returnList.add(rowObject);
		}
		return returnList;
	}
}

