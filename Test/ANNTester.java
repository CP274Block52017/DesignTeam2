

import static org.junit.Assert.*;

import org.junit.Test;
import org.neuroph.core.data.DataSet;

import NeuralNetwork.dataFormatter;
import NeuralNetwork.ANNMetricRetriever;
import NeuralNetwork.BackPropagatingNN;

public class ANNTester {
	
	//tests functionality of dataFormatter. Makes sure separates into two sets..
	@Test
	public void dataParsing() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertTrue(trainingAndTestSet.length == 2);
	}
	//tests functionality of dataFormatter.. Makes sure separates into two one larger and one smaller set
	@Test
	public void dataParsingSeperateSizes() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
	}
	//Makes a BackPropagatingNN and configures it.
	@Test
	public void configuratonDoesNotCrash() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
		BackPropagatingNN ANN = new BackPropagatingNN();
		ANN.configure(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		assertTrue(true);
	}
	//Makes sure BackPropagatingNN completes training.
	@Test
	public void trainingCompletes() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
		BackPropagatingNN ANN = new BackPropagatingNN();
		ANN.configure(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		ANN.train(trainingAndTestSet[0]);
		assertTrue(true);
	}

	@Test
	//Had to use multiple asserts in one statement. Takes way to long in instantiate, train, and test a neural network each time. I'm open to solutions!!
	//Makes sure all metrics are in correct range.
	public void metricsInRange() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
		BackPropagatingNN ANN = new BackPropagatingNN();
		ANN.configure(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		ANN.train(trainingAndTestSet[0]);
		ANNMetricRetriever tester = new ANNMetricRetriever(ANN);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean standdevWorking = tester.getStandardDeviation() < 17000;
		boolean getPercentAccWorking =  (tester.getPercentExactEstimates() <= 100)
		& (tester.getPercentExactEstimates() >= 0);
		boolean getPercentOverWorking = (tester.getPercentOverestimated() <= 100)
				& (tester.getPercentOverestimated() >= 0);
		boolean getPercentUnderWorking = (tester.getPercentUnderestimated() <= 100)
				& (tester.getPercentUnderestimated() >= 0);
		
		assertTrue(standdevWorking);
		assertTrue(getPercentAccWorking);
		assertTrue(getPercentOverWorking);
		assertTrue(getPercentUnderWorking);
	}
	//Had to use multiple asserts in one statement. Takes way to long in instantiate, train, and test a neural network each time. I'm open to solutions!!
	//Makes sure all results are reasonably accurate and not off by 1000 or 5000 on average.
	@Test
	public void accurateResults() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
		BackPropagatingNN ANN = new BackPropagatingNN();
		ANN.configure(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		ANN.train(trainingAndTestSet[0]);
		ANNMetricRetriever tester = new ANNMetricRetriever(ANN);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean standdevUnder5000 = tester.getStandardDeviation() < 5000;
		boolean standdevUnder1000 = tester.getStandardDeviation() < 1000;
		assertTrue(standdevUnder5000);
		assertTrue(standdevUnder1000);
		tester.printResults();
	}

}
