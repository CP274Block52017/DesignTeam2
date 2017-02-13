package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

//current interface for ANN in case other types of ANN are made. Not really unsed as of now. Needs to be expanded upon. 
public interface ANN {
    public void configure(int inputSize, int outputSize, 
    double learnRate, double maxError, int maxIterations);
    public void train(DataSet trainingSet);
    public int getPredictedOutput(DataSetRow input);
    public int getActualOutput(DataSetRow input);
}

