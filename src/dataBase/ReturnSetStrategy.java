package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * This interface serves to convert CSV reader outputs to usable objects for  
 * preprocessing. 
 */

public interface ReturnSetStrategy {
	List<DataObject> returnSetToDataObject(ResultSet resultSet) throws SQLException;
}
