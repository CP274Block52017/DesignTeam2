

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.neuroph.core.data.DataSet;

import neuralNetwork.BackPropagatingConfigurationStrategy;
import neuralNetwork.ConfigurationObject;
import neuralNetwork.NeuralNetworkController;
import neuralNetwork.NeuralNetworkMetrics;
import neuralNetwork.dataFormatter;

public class NeuralNetworkTester {
	DataSet[] trainingAndTestSet;
	@Before
	public void setup(){
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/testData.txt", 9, 16);
    	trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
	}
	
	public NeuralNetworkController getController(){
		ConfigurationObject configurationObject = new ConfigurationObject(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		BackPropagatingConfigurationStrategy backPropConfigStrategy = new BackPropagatingConfigurationStrategy();
    	NeuralNetworkController neuralNetworkController = new NeuralNetworkController(backPropConfigStrategy);
    	neuralNetworkController.configure(configurationObject);
    	return neuralNetworkController;
	}
	//Makes a BackPropagatingNN and configures it.
	@Test
	public void configuratonDoesNotCrash() {
    	getController();
		assertTrue(true);
	}
	//Makes sure BackPropagatingNN completes training.
	@Test
	public void trainingCompletes() {
		NeuralNetworkController neuralNetworkController = getController();
		neuralNetworkController.train(trainingAndTestSet[0]);
		assertTrue(true);
	}

	@Test
	//Had to use multiple asserts in one statement. Takes way to long in instantiate, train, and test a neural network each time. I'm open to solutions!!
	//Makes sure all metrics are in correct range.
	public void standarDeviationWorking() {
		NeuralNetworkController neuralNetworkController = getController();
		neuralNetworkController.train(trainingAndTestSet[0]);
		NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean standdevWorking = tester.getStandardDeviation() < 17000;
		
		assertTrue(standdevWorking);
	}
	
	@Test
	public void getPercentAccWorkingCorrect() {
		NeuralNetworkController neuralNetworkController = getController();
		neuralNetworkController.train(trainingAndTestSet[0]);
		NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean getPercentAccWorking =  (tester.getPercentExactEstimates() <= 100)
		& (tester.getPercentExactEstimates() >= 0);
		
		assertTrue(getPercentAccWorking);
	}
	
	@Test
	public void getPercentOverWorkingisCorrect() {
		NeuralNetworkController neuralNetworkController = getController();
		neuralNetworkController.train(trainingAndTestSet[0]);
		NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean getPercentOverWorking = (tester.getPercentOverestimated() <= 100)
				& (tester.getPercentOverestimated() >= 0);

		assertTrue(getPercentOverWorking);
	}
	
	@Test
	public void percentUnderWorkingisCorrect() {
		NeuralNetworkController neuralNetworkController = getController();
		neuralNetworkController.train(trainingAndTestSet[0]);
		NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean getPercentUnderWorking = (tester.getPercentUnderestimated() <= 100)
				& (tester.getPercentUnderestimated() >= 0);
		
		assertTrue(getPercentUnderWorking);
	}
	//Had to use multiple asserts in one statement. Takes way to long in instantiate, train, and test a neural network each time. I'm open to solutions!!
	//Makes sure all results are reasonably accurate and not off by 1000 or 5000 on average.
	@Test
	public void accurateResults() {
		NeuralNetworkController neuralNetworkController = getController();
		neuralNetworkController.train(trainingAndTestSet[0]);
		NeuralNetworkMetrics tester = new NeuralNetworkMetrics(neuralNetworkController);
		
		tester.setMetrics(trainingAndTestSet[1]);
		boolean standdevUnder5000 = tester.getStandardDeviation() < 5000;
		boolean standdevUnder1000 = tester.getStandardDeviation() < 1000;
		assertTrue(standdevUnder5000&&standdevUnder1000);
		tester.printResults();
	}

}
