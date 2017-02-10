

import static org.junit.Assert.*;

import org.junit.Test;
import org.neuroph.core.data.DataSet;

import NeuralNetwork.dataFormatter;
import NeuralNetwork.testPerceptron;

public class ANNTester {
	
	//tests functionality of dataFormatter. Makes sure separates into two sets
	@Test
	public void dataParsing() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertTrue(trainingAndTestSet.length == 2);
	}
	//tests functionality of dataFormatter. Makes sure separates into two one larger and one smaller set
	@Test
	public void dataParsingSeperateSizes() {
		dataFormatter d = new dataFormatter();
    	DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
    	DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
    	assertTrue(trainingAndTestSet[0].size() > trainingAndTestSet[1].size());
	}
	//makes sure ANN doesn't crash!!
	@Test
	public void runANN() {
		dataFormatter d = new dataFormatter();
		DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
		DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
		testPerceptron p = new testPerceptron();
		p.run(trainingAndTestSet[0], trainingAndTestSet[1], 30, 2);
		p.print();
		assertTrue(true); //it doesn't crash!!
	}
	//makes sure percent correct is in proper range
	@Test
	public void getPercentCorrect() {
		dataFormatter d = new dataFormatter();
		DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
		DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
		testPerceptron p = new testPerceptron();
		p.run(trainingAndTestSet[0], trainingAndTestSet[1], 30, 2);
		double percentCorrect = p.getPercentCorrect();
		assertTrue((percentCorrect > 0) && (percentCorrect < 100.01)); 
	}
	//makes sure percent correct is over 50
	@Test
	public void accuracyOver50() {
		dataFormatter d = new dataFormatter();
		DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
		DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
		testPerceptron p = new testPerceptron();
		p.run(trainingAndTestSet[0], trainingAndTestSet[1], 30, 2);
		assertTrue(p.getPercentCorrect()>50); 
	}
	//makes sure percent correct is over 65. Working adequately.
	@Test
	public void accuracyOver65() {
		dataFormatter d = new dataFormatter();
		DataSet normalizedSet = d.getNormalizedSet("Data/breastcancer.txt", 30, 2);
		DataSet[] trainingAndTestSet = d.getTrainingandTest(normalizedSet, 70, 30);
		testPerceptron p = new testPerceptron();
		p.run(trainingAndTestSet[0], trainingAndTestSet[1], 30, 2);
		assertTrue(p.getPercentCorrect()>70); 
	}
	



}
