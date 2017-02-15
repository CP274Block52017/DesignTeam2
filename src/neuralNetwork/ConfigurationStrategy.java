package neuralNetwork;

import org.neuroph.core.NeuralNetwork;

/**
 * Strategy interface for implementation of concrete strategies. All use a configuration object for the instantiation of the 
 * Neural Network. 
 *
 */
public interface ConfigurationStrategy {
	public NeuralNetwork configure(ConfigurationObject configurationObject);
}
