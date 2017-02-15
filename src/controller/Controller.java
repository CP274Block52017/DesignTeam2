package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.neuroph.core.data.DataSet;

import dataBase.DBConfig;
import dataBase.DJReturnSetStrategy;
import dataBase.DJWriteStrategy;
import dataBase.DataObject;
import dataBase.DatabaseController;
import dataBase.NSWriteStrategy;
import dataBase.DayStrings;
import dataBase.DayStringsReturnSetStrategy;
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
	}
	
	private static void setUp() throws SQLException, ParseException{
		MySQLInitializer.getInstance().setUpDatabase();
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
}
