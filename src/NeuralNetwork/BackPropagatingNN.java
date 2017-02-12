package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

//TODO: remove magic numbers. Hidden Node

public class BackPropagatingNN implements ANN {

    MultiLayerPerceptron neuralNet;

    //Create neural network and train 
    public void configure(int inputSize, int outputSize, double learnRate, double maxError, int maxIterations) {
    	
    	//initialize neural network
        neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputSize, 20, outputSize);
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(learnRate);
        learningRule.setMaxError(maxError);
        learningRule.setMaxIterations(maxIterations);
    }
    
    public void train(DataSet trainingSet) {
        System.out.println("Training Started...");
        neuralNet.learn(trainingSet);
        System.out.println("Training Done....");
    }
    
    public int getPredictedOutput(DataSetRow input) {
    	this.neuralNet.setInput(input.getInput());
        this.neuralNet.calculate();
        double[] networkOutput = neuralNet.getOutput();
        return binToInt(networkOutput);
    }
    
    public int getActualOutput(DataSetRow input) {
    	return binToInt(input.getDesiredOutput());
    }
    
    private int binToInt(double bin[]) {
    	int base10Num = 0;
    	for( int i = 0; i < bin.length; i++ ) {
    		base10Num += (Math.pow(2, i) * bin[bin.length-1-i]);
    	}
    	return base10Num;
    	
    }
    

    
    
    
}