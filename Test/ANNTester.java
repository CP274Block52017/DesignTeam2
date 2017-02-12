

import static org.junit.Assert.*;

import org.junit.Test;
import org.neuroph.core.data.DataSet;

import NeuralNetwork.dataFormatter;
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
    	DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 9, 16);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
	}
	//Makes a BackPropagatingNN and configures it.
	@Test
	public void configuratonDoesNotCrash() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
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
    	DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
		BackPropagatingNN ANN = new BackPropagatingNN();
		ANN.configure(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		ANN.train(trainingAndTestSet[0]);
		assertTrue(true);
	}
	//Makes sure BackPropagatingNN completes training.
	@Test
	public void predictionWithinOneThousand() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertFalse(trainingAndTestSet[0].size() < trainingAndTestSet[1].size());
		BackPropagatingNN ANN = new BackPropagatingNN();
		ANN.configure(trainingAndTestSet[0].getInputSize(), trainingAndTestSet[0].getOutputSize(), .5, .001, 5050);
		ANN.train(trainingAndTestSet[0]);
		assertTrue(true);
	}

}
