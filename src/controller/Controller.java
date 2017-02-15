package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.neuroph.core.data.DataSet;

import dataBase.CSVFileReader;
import dataBase.DJObject;
import dataBase.DBConfig;
import dataBase.DJReturnSetStrategy;
import dataBase.DJWriteStrategy;
import dataBase.DataObject;
import dataBase.DatabaseController;
import dataBase.DateStringReturnSetStrategy;
import dataBase.NSWriteStrategy;
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
	public static void main(String [] args) throws SQLException, ParseException, IOException{
		setUp();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter start date in format yyyy-mm-dd");
        String fromDate = br.readLine();
        System.out.println("Enter end date in format yyyy-mm-dd");
        String toDate = br.readLine();
        DataSet[] dataSets = getDataSets(fromDate,toDate);
        NeuralNetworkController neuralNetworkController = buildNN(dataSets[0]);
        neuralNetworkController.train(dataSets[0]);
        testAndDisplayResults(neuralNetworkController, dataSets[1]);
        /*NeuralNetworkController neuralNetworkController = buildAndTrainNNOnDataFromRange(fromDate,toDate);
        
		ListStringArraysToNSObject nsConverter = new ListStringArraysToNSObject();
		List<DataObject> NSList = nsConverter.stringtoDataObject(redditNewsList);
		
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
*/	}
	
	private static void setUp() throws SQLException, ParseException{
		MySQLInitializer initializer = new MySQLInitializer();
		initializer.setUpDatabase();
	}
	
	private static void testAndDisplayResults(NeuralNetworkController neuralNetworkController, DataSet testSet){
		NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		tester.setMetrics(testSet);
		tester.printResults();
	}
	
	private static DataSet[] getDataSets(String fromDate,String toDate) throws SQLException, FileNotFoundException, ParseException{
		List<DataObject> nsDataObjectList = getNewsFromTable(fromDate,toDate);
		List<DataObject> djObjectList = getDJFromTable(fromDate,toDate);
		List<int[]> processedNSData = processNSObjectList(nsDataObjectList, djObjectList);
		return getDataSets(processedNSData);
	}
	
	private static NeuralNetworkController buildNN(DataSet trainingSet) throws SQLException, FileNotFoundException, ParseException{
		BackPropagatingConfigurationStrategy backPropConfigStrategy = new BackPropagatingConfigurationStrategy();
    	NeuralNetworkController neuralNetworkController = new NeuralNetworkController(backPropConfigStrategy);
    	return configureNNC(neuralNetworkController,trainingSet);
	}
	
	private static NeuralNetworkController configureNNC(NeuralNetworkController neuralNetworkController,DataSet trainingSet){
		ConfigurationObject configurationObject = new ConfigurationObject(trainingSet.getInputSize(), trainingSet.getOutputSize(), .5, .001, 5050);
		neuralNetworkController.configure(configurationObject);
		return neuralNetworkController;
	}
	
	private static DataSet[] getDataSets(List<int[]> processedNSData){
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(processedNSData);
		return dataFormatter.getTrainingandTest(dataSet, 70, 30);
	}
	
	private static List<int[]> processNSObjectList(List<DataObject> nsDataObjectList, List<DataObject> djObjectList) throws FileNotFoundException, SQLException, ParseException{
		PreprocessingController preprocessingController = new PreprocessingController();
		List<DayStrings> removedPrepsList = preprocessingController.removePrepositions(nsDataObjectList); 
		return preprocessingController.getNNList(removedPrepsList, djObjectList);
	}
	
	private static List<DataObject> getNewsFromTable(String fromDate, String toDate) throws SQLException{
		NSWriteStrategy NSWriteStrategy = new NSWriteStrategy();
		DayStringsReturnSetStrategy NSReturnStrategy = new DayStringsReturnSetStrategy();
		DatabaseController NSController = new DatabaseController(NSWriteStrategy, NSReturnStrategy,"jdbc:mysql://localhost:"+DBConfig.localhostID+"/omnipredictor?user="+ DBConfig.username +"&password=" + DBConfig.password);
		ResultSet resultSet = NSController.retrieveDataFromDB("NewsHeadlines", fromDate, toDate);
		return NSController.returnSetStrategy(resultSet);
	}
	
	private static List<DataObject> getDJFromTable(String fromDate, String toDate) throws SQLException{
		DJWriteStrategy DJWriteStrategy = new DJWriteStrategy();
		DJReturnSetStrategy DJReturnStrategy = new DJReturnSetStrategy();
		DatabaseController DJController = new DatabaseController(DJWriteStrategy, DJReturnStrategy,"jdbc:mysql://localhost:"+DBConfig.localhostID+"/omnipredictor?user="+ DBConfig.username +"&password=" + DBConfig.password);
		ResultSet resultSet = DJController.retrieveDataFromDB("DJOpening", fromDate, toDate);
		return DJController.returnSetStrategy(resultSet);
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
