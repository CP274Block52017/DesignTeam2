package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eli
 *
 */
public class DateStringReturnSetStrategy implements ReturnSetStrategy {

	@Override
	public List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException {
		List<DataObject> returnList = new ArrayList<DataObject>();
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			String headline = resultSet.getString("headline");
			DateStringObject rowObject = new DateStringObject(date,headline);
			returnList.add(rowObject);
		}
		return returnList;
	}
}

