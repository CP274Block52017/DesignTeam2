package DataManipulation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface RetrieveStrategy {
	ResultSet retrieveData(Connection databaseConnection)  throws SQLException;
}
