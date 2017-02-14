package preprocessing;

import java.sql.SQLException;
import java.util.List;

import DataManipulation.CSVFileReader;
import DataManipulation.MySQLInitializer;

public class Controller {
	public void getNNOutput() throws SQLException{
		CSVFileReader reader = new CSVFileReader();
		List<String[]>redditNewsList = reader.readFile("Data/RedditNews.csv");
		List<String[]>DJList = reader.readFile("Data/DJIA_table.csv");
		MySQLInitializer initializer = new MySQLInitializer();
		initializer.setUp();
	}
}
