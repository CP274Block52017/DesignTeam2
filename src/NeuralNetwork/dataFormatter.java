package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.util.data.norm.MaxNormalizer;
import org.neuroph.util.data.norm.Normalizer;

public class dataFormatter {
	
    public DataSet getNormalizedSet(String trainingSetFileName, int inputsCount, int outputsCount ) {
        DataSet dataSet = DataSet.createFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
        dataSet.shuffle();
        Normalizer normalizer = new MaxNormalizer();
        normalizer.normalize(dataSet);
		return dataSet;
    	
        }
    public DataSet[] getTrainingandTest(DataSet dataSet, int TrainingSize, int TestSize) {
    	DataSet[] trainingAndTestSet = dataSet.createTrainingAndTestSubsets(70, 30);
    	return trainingAndTestSet;
    }

}
