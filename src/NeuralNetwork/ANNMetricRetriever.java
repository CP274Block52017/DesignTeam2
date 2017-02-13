package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class ANNMetricRetriever {
	
	ANN neuralNet;
	int standardDeviation = 0;
	int exactEstimates = 0;
	int overestimates = 0;
	int underestimates = 0;
	int total = 0;
	
	public ANNMetricRetriever(ANN neuralNet) {
		this.neuralNet = neuralNet;
	}
	public int setMetrics(DataSet testSet) {
		total = testSet.size();
        for (DataSetRow testSetRow : testSet.getRows()) {
        	int prediction = neuralNet.getPredictedOutput(testSetRow);
        	int actual = neuralNet.getActualOutput(testSetRow);
        	System.out.println("Predicted: " + prediction + " Actual: " + actual);
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
