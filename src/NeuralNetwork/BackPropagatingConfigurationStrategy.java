package NeuralNetwork;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 * This strategy creates a MultiLayer Perceptron Neural Network using a sigmoid activation function with back propagation.
 * The strategy takes in a configuration object which contains all the parameters for instantiating the neural network.
 *
 */
public class BackPropagatingConfigurationStrategy implements ConfigurationStrategy{
	public NeuralNetwork configure(ConfigurationObject configurationObject){
		//initialize neural network with 20 nodes in hidden layer. Sigmoid activation nodes with Back Propagation
    	int hiddenLayerSize = 20;
    	MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, configurationObject.inputSize, hiddenLayerSize, configurationObject.outputSize);
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(configurationObject.learnRate);
        learningRule.setMaxError(configurationObject.maxError);
        learningRule.setMaxIterations(configurationObject.maxIterations);
        return neuralNet;
	}
}
