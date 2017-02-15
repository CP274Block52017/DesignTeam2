package neuralNetwork;


/**
 * This object holds all the values for the instantiation of a strategy so that a large number of parameters don't have to be passed into
 * configure. All are standard neural network configuration variables.
 *
 */
public class ConfigurationObject {
	public int inputSize;
	public int outputSize;
	public double learnRate;
	public double maxError;
	public int maxIterations;
	
	public ConfigurationObject(int inputSize, int outputSize, double learnRate, double maxError, int maxIterations){
		this.inputSize = inputSize;
		this.outputSize = outputSize;
		this.learnRate = learnRate;
		this.maxError = maxError;
		this.maxIterations = maxIterations;
	}
}
