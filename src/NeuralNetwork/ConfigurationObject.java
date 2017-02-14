
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
