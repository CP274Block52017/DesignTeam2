package NeuralNetwork;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;


public class BackPropagatingNN implements ANN {

    MultiLayerPerceptron neuralNet;

    //Create neural network and train 
    public void configure(int inputSize, int outputSize, double learnRate, double maxError, int maxIterations) {
    	
    	//initialize neural network with 20 nodes in hidden layer. Sigmoid activation nodes with Back Propagation
    	int hiddenLayerSize = 20;
        neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputSize, hiddenLayerSize, outputSize);
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
    //Gets predicted output on trained network for specific day.
    public int getPredictedOutput(DataSetRow input) {
    	this.neuralNet.setInput(input.getInput()); 
        this.neuralNet.calculate();
        double[] networkOutput = neuralNet.getOutput(); //gets predicted output
        return binToInt(networkOutput);
    }
    //Gets actual output on trained network for specific day. Used for comparison with prediction.
    public int getActualOutput(DataSetRow input) {
    	return binToInt(input.getDesiredOutput());
    }
    //Converts Binary number to Integer for understandability and Neural Network testing/optimization 
    private int binToInt(double bin[]) {
    	int base10Num = 0;
    	for( int i = 0; i < bin.length; i++ ) {
    		base10Num += (Math.pow(2, i) * bin[bin.length-1-i]);
    	}
    	return base10Num;
    	
    }
    

    
    
    
}