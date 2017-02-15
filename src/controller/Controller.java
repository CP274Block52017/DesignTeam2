package controller;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.neuroph.core.data.DataSet;

import dataBase.CSVFileReader;
import dataBase.DJObject;
import dataBase.DJReturnSetStrategy;
import dataBase.DJWriteStrategy;
import dataBase.DataObject;
import dataBase.DatabaseController;
import dataBase.DateStringReturnSetStrategy;
import dataBase.DateStringWriteStrategy;
import dataBase.DayStrings;
import dataBase.DayStringsReturnSetStrategy;
import dataBase.ListStringArraysToDJObject;
import dataBase.ListStringArraysToNSObject;
import dataBase.MySQLInitializer;
import neuralNetwork.BackPropagatingConfigurationStrategy;
import neuralNetwork.ConfigurationObject;
import neuralNetwork.NeuralNetworkController;
import neuralNetwork.NeuralNetworkDataFormatter;
import neuralNetwork.NeuralNetworkMetrics;
import preprocessing.PreprocessingController;

public class Controller {
	public static void main(String [] args) throws SQLException, ParseException, FileNotFoundException{
		CSVFileReader reader = new CSVFileReader();
		List<String[]> redditNewsList = reader.readFile("Data/RedditNews.csv");
		List<String[]> DJList = reader.readFile("Data/DJIA_table.csv");
		MySQLInitializer initializer = new MySQLInitializer();
		initializer.setUp();
		String localhostID = "8889";
		String username = "root";
		String password = "root";
		ListStringArraysToDJObject djConverter = new ListStringArraysToDJObject();
		List<DataObject> DJObject = djConverter.stringtoDataObject(DJList);
		DJWriteStrategy DJWriteStrategy = new DJWriteStrategy();
		DJReturnSetStrategy DJReturnStrategy = new DJReturnSetStrategy();
		DatabaseController DJController = new DatabaseController(DJWriteStrategy, DJReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
		DJController.writeListtoDB(DJObject);
		ListStringArraysToNSObject nsConverter = new ListStringArraysToNSObject();
		List<DataObject> NSList = nsConverter.stringtoDataObject(redditNewsList);
		DateStringWriteStrategy NSWriteStrategy = new DateStringWriteStrategy();
		DateStringReturnSetStrategy NSReturnStrategy = new DateStringReturnSetStrategy();
		DatabaseController NSController = new DatabaseController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password);
		NSController.writeListtoDB(NSList);
		ResultSet nsReturnList = NSController.retrieveDataFromDB("NewsHeadlines", "2016-06-28", "2016-07-01");
		DayStringsReturnSetStrategy dateStringsReturn = new DayStringsReturnSetStrategy();
		//System.out.println("result set");
		//printResultSet(nsReturnList);
		List<DayStrings> nsDayStringsList = dateStringsReturn.returnSetToDataObject(nsReturnList);
		PreprocessingController preprocessingController = new PreprocessingController();
		//System.out.println("day strings list");
		//printDayStringsList(nsDayStringsList);
		List<DayStrings> removedPrepsList = preprocessingController.removePrepositions(nsDayStringsList);
		//System.out.println("removed prep list");
		//printDayStringsList(removedPrepsList);
		List<int[]> wordCounts = preprocessingController.getNNList(removedPrepsList, DJObject);
		System.out.println("word counts");
		for(int[] i : wordCounts){
			System.out.println(Arrays.toString(i));
		}
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(wordCounts);
		DataSet[] trainingAndTestSet = dataFormatter.getTrainingandTest(dataSet, 70, 30);
		ConfigurationObject configurationObject = new ConfigurationObject(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		BackPropagatingConfigurationStrategy backPropConfigStrategy = new BackPropagatingConfigurationStrategy();
    	NeuralNetworkController neuralNetworkController = new NeuralNetworkController(backPropConfigStrategy);
    	neuralNetworkController.configure(configurationObject);
    	neuralNetworkController.train(trainingAndTestSet[0]);
    	NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		tester.setMetrics(trainingAndTestSet[1]);
		tester.printResults();
		//NSController.deleteAll("DJOpening");
		//NSController.deleteAll("NewsHeadlines");
	}
	
	private static void printResultSet(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd = rs.getMetaData();
		   System.out.println("querying SELECT * FROM XXX");
		   int columnsNumber = rsmd.getColumnCount();
		   while (rs.next()) {
		       for (int i = 1; i <= columnsNumber; i++) {
		           if (i > 1) System.out.print(",  ");
		           String columnValue = rs.getString(i);
		           System.out.print(columnValue + " " + rsmd.getColumnName(i));
		       }
		       System.out.println("");
		   }
	}
		   
	private static void printDayStringsList(List<DayStrings> dayStringsList){
		for(DayStrings i : dayStringsList){
			System.out.println(i.getDate());
			System.out.println(Arrays.toString(i.getStringArray()));
		}
	}
}
