package dataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;


/**
 * This class serves to create the database "Omnipredictor" if it does not already exist.
 * It initializes tables: "DJOpening" and "NewsHeadlines", reads the csv files, and writes them to the tables
 *
 */

public final class MySQLInitializer {

	Connection databaseConnection = null;
	
	//localhostID, username, and password are set to default MySQL.These are set in DBConfig;
	private String mySQLConnectionAddress = DBConfig.mySQLConnectionAddress;
	private String databaseConnectionAddress = DBConfig.databaseConnectionAddress;
	
	public static  MySQLInitializer instance;
	
	public static  MySQLInitializer getInstance(){
		if(instance == null){
			instance = new MySQLInitializer();
		}
		return instance;
	}
	
	public void setUpDatabase() throws SQLException, ParseException{
		if(!DBExists()){
			createDB();
			createDJIATable();
			createNewsStoriesTable();
			readAndWriteCSVToDB();
		}
	}
	
	private boolean DBExists() throws SQLException{
		String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'omnipredictor'";
		Connection createConnection = DriverManager.getConnection(mySQLConnectionAddress);
		Statement databaseStatement = createConnection.createStatement();
		ResultSet resultSet = databaseStatement.executeQuery(query);
		if(resultSet.next()){
			return true;
		}
		return false;
	}
	
	private void createDB() throws SQLException{
		String query = "create database omnipredictor";
		Connection createConnection = DriverManager.getConnection(mySQLConnectionAddress);
		Statement databaseStatement = createConnection.createStatement();
		databaseStatement.execute(query);
		databaseConnection = DriverManager.getConnection(databaseConnectionAddress);
	}
	
	private void createDJIATable() throws SQLException{
		String query = "CREATE TABLE `DJOpening` (`id` int(11) NOT NULL,`date` date DEFAULT NULL,`opening` decimal(10,5) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		Statement databaseStatement = databaseConnection.createStatement();
		databaseStatement.execute(query);
		query = "ALTER TABLE `DJOpening` ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`)";
		databaseStatement = databaseConnection.createStatement();
		databaseStatement.execute(query);
		query = "ALTER TABLE `DJOpening` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1470;";
		databaseStatement = databaseConnection.createStatement();
		databaseStatement.execute(query);
	}
	
	private void createNewsStoriesTable() throws SQLException{
		String query = "CREATE TABLE `NewsHeadlines` (`id` int(11) NOT NULL,`date` date DEFAULT NULL,`headline` text) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		Statement databaseStatement = databaseConnection.createStatement();
		databaseStatement.execute(query);
		query = "ALTER TABLE `NewsHeadlines` ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);";
		databaseStatement = databaseConnection.createStatement();
		databaseStatement.execute(query);
		query = "ALTER TABLE `NewsHeadlines` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1470;";
		databaseStatement = databaseConnection.createStatement();
		databaseStatement.execute(query);
	}
	
	private void readAndWriteCSVToDB() throws SQLException, ParseException{
		CSVFileReader reader = new CSVFileReader();
		List<String[]> newsHeadlinesStrings = reader.readFile("Data/RedditNews.csv");
		ListStringArraysToNSObject nsConverter = new ListStringArraysToNSObject();
		List<DataObject> newsHeadlinesObjects = nsConverter.stringtoDataObject(newsHeadlinesStrings);
		NSWriteStrategy nsWriter = new NSWriteStrategy();
		nsWriter.writeToTable(newsHeadlinesObjects, databaseConnection);
		List<String[]> djStrings = reader.readFile("Data/DJIA_table.csv");
		ListStringArraysToDJObject djConverter = new ListStringArraysToDJObject();
		List<DataObject> djObjects = djConverter.stringtoDataObject(djStrings);
		DJWriteStrategy djWriter = new DJWriteStrategy();
		djWriter.writeToTable(djObjects, databaseConnection);
	}
}
