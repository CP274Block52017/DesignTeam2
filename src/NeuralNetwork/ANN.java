package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public interface ANN {
    public void configure(int inputSize, int outputSize, 
    double learnRate, double maxError, int maxIterations);
    public void train(DataSet trainingSet);
    public int getPredictedOutput(DataSetRow input);
    public int getActualOutput(DataSetRow input);
}

