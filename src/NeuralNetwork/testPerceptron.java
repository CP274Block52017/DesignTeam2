package NeuralNetwork;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

public class testPerceptron {

    int numCorrect = 0;
    int total = 0;


    public void run(DataSet trainingSet, DataSet testSet, int inputsCount, int outputsCount) {

        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(inputsCount, 16, outputsCount);
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.5);
        learningRule.setMaxError(0.001);
        learningRule.setMaxIterations(6000);
        neuralNet.learn(trainingSet);
        testNeuralNetwork(neuralNet, testSet);

    }
    

    private void testNeuralNetwork(MultiLayerPerceptron neuralNet, DataSet testSet) {

        for (DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();
            int predicted = maxOutput(networkOutput);

            double[] networkDesiredOutput = testSetRow.getDesiredOutput();
            int actual = maxOutput(networkDesiredOutput);
            keepScore(predicted, actual);
        }


    }
    
 
    private static int maxOutput(double[] array) {
        double max = array[0];
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                index = i;
                max = array[i];
            }
        }
        return index;
    }

    private void keepScore(int prediction, int actual) {
        total++;

        if (prediction == actual) {
            numCorrect++;
        }
    }
    
    
    public void print() {
    	System.out.println(" Percent Correct: " + getPercentCorrect() +"%");
    	System.out.println(" Total Entries: " + total);
    	System.out.println(" Number Correct: " + numCorrect);
    }
    
    public double getPercentCorrect() {
    	return ((double)this.numCorrect / (double)this.total) * 100;
    }
    
    
}