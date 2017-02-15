package NeuralNetwork;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;


/**
 * NeuralNetwork that trains on data set, predicts the output for a row, get's the actual output for a row, and converts it to base
 * 10 for understandability. Uses a strategy pattern for picking the specific type of neural network.
 *
 */
public class NeuralNetworkController{

    NeuralNetwork neuralNet;
    ConfigurationStrategy configurationStrategy;

    public NeuralNetworkController(ConfigurationStrategy configurationStrategy){
    	this.configurationStrategy = configurationStrategy;
    }
    
    //Create neural network and train 
    public void configure(ConfigurationObject configurationObject) {
    	this.neuralNet = configurationStrategy.configure(configurationObject);
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