package NeuralNetwork;

import org.neuroph.core.NeuralNetwork;

public interface ConfigurationStrategy {
	public NeuralNetwork configure(ConfigurationObject configurationObject);
}
