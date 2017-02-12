package DataManipulation.ReturnSetStrategyInterfaceClasses;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataManipulation.DataObjectInterfaceClasses.DJObject;
import DataManipulation.DataObjectInterfaceClasses.DataObject;

public class DJReturnSetStrategy implements ReturnSetStrategy {

	@Override
	public List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException {
		List<DataObject> returnList = new ArrayList<DataObject>();
		while (resultSet.next()) {
			java.sql.Date date = resultSet.getDate("date");
			BigDecimal openingValue = resultSet.getBigDecimal("opening");
			DJObject rowObject = new DJObject(date,openingValue);
			returnList.add(rowObject);
		}
		return returnList;
	}
}
