package neuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.util.data.norm.MaxNormalizer;
import org.neuroph.util.data.norm.Normalizer;



//helper object as of now. For testing on CSV files rather than running whole program.
//Not for production implementation
/**
 * Helper class/driver for feeding information from CSV files into the NN instead of waiting for database. Separates 
 * testing unit by unit.
 *
 */
public class dataFormatter {

	//Read CSV file containing data into a Neuroph DataSet, randomize order, and format
	public DataSet getNormalizedSet(String trainingSetFileName, int inputsCount, int outputsCount ) {
		DataSet dataSet = DataSet.createFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
		dataSet.shuffle(); //Shuffles data to ensure that the test and training data vary. 
		Normalizer normalizer = new MaxNormalizer();
		normalizer.normalize(dataSet);
		return dataSet;

	}
	
	//Specify percentages of data used for training and test DataSets, respectively, return array containing both	
	public DataSet[] getTrainingandTest(DataSet dataSet, int TrainingSize, int TestSize) {
		DataSet[] trainingAndTestSet = dataSet.createTrainingAndTestSubsets(70, 30);
		return trainingAndTestSet;
	}
	
	

}
