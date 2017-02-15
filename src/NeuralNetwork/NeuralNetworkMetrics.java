package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * Object for testing efficiency and accurateness of NN. For statistical model information for user and metrics for model
 * reconfiguration for programmer. 
 *
 */
public class NeuralNetworkMetrics {
	
	NeuralNetworkController neuralNetworkController;
	int standardDeviation = 0; //standard deviation between actual and predicted DIJA
	int exactEstimates = 0;
	int overestimates = 0;
	int underestimates = 0;
	int total = 0;
	
	public NeuralNetworkMetrics(NeuralNetworkController neuralNet) {
		this.neuralNetworkController = neuralNet;
	}
	public int setMetrics(DataSet testSet) {
		total = testSet.size();
        for (DataSetRow testSetRow : testSet.getRows()) { //gets each row in the testSet
        	int prediction = neuralNetworkController.getPredictedOutput(testSetRow); //records if the prediction was over, under, or exactly the actual dow.
        	int actual = neuralNetworkController.getActualOutput(testSetRow);
        	standardDeviation += Math.abs(prediction-actual);
        	if(actual == prediction) {exactEstimates++;}
        	else if(actual > prediction) {overestimates++;}
        	else if(actual < prediction) {underestimates++;}
        }
        standardDeviation = standardDeviation/testSet.size();
		return 1;
	}
	public int getStandardDeviation() {
		return this.standardDeviation;
	}
	public float getPercentExactEstimates() {
		return (100*(float)this.exactEstimates/this.total);
	}
	public float getPercentOverestimated() {
		return (100*(float)this.overestimates/this.total);
	}
	public float getPercentUnderestimated() {
		return (100*(float)this.underestimates/this.total);
	}
	//Formated print statement that allows the user to see all metrics of the NN.
	public void printResults() {
		System.out.println("*************ANN METRICS*************");
		System.out.println("Total Cases: " + this.total);
		System.out.println("Exact Estimates: " + this.exactEstimates);
		System.out.println("Percent Exact Estimates: " + getPercentExactEstimates() + "%");
		System.out.println("Overestimates: " + this.overestimates);
		System.out.println("Percent Overestimated: " + getPercentOverestimated() + "%");
		System.out.println("Underestimates: " + this.underestimates);
		System.out.println("Percent Underestimated: " + getPercentUnderestimated() + "%");
		System.out.println("Standard Deviation: " + this.standardDeviation);
		System.out.println("*************************************");
	}
	
	

}
