import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.neuroph.core.data.DataSet;

import junit.framework.Assert;
import neuralNetwork.NeuralNetworkDataFormatter;

public class ANNDataFormatterTester {

	//Note: data set objects have row objects with an input and output section
	@Test
	public void toDataSetInstantiationWorks() {
		ArrayList<int[]> inputList = new ArrayList<int[]>();
		inputList.add(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21});
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(inputList);
		//Check to make sure it didnt crash!!!!!!
		assertTrue(true);
	}
	//makes sure that after the object is created, the input and outputs are correct.
	@Test
	public void toDataSetInputsandOutputsCorrect() {
		ArrayList<int[]> inputList = new ArrayList<int[]>();
		inputList.add(new int[]{1,2,3,4,5,6,7});
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(inputList);
		boolean correctInput = dataSet.getInputSize() ==  6;
		boolean correctOutput = dataSet.getOutputSize() == 16;
		assertTrue(correctInput & correctOutput );
	}
	//Makes sure that the Binary output array is correct
	@Test
	public void toCorrectBinary() {
		ArrayList<int[]> inputList = new ArrayList<int[]>();
		inputList.add(new int[]{1,2,3,4,5,6,7});
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(inputList);
		boolean correctOutput = dataSet.getRowAt(0).getDesiredOutput()[15] == 1;
		System.out.println(dataSet.getRowAt(0).getDesiredOutput()[15]);
		assertTrue(correctOutput );
	}
	//Makes sure that the Binary output array is correct for an input that isn't one
	@Test
	public void toCorrectBinaryForNotOne() {
		ArrayList<int[]> inputList = new ArrayList<int[]>();
		inputList.add(new int[]{12,2,3,4,5,6,7});
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(inputList);
		double[] correctArray = new double[16];
		for(int i=0;i<16;i++){
			if(i==12||i==13){
				correctArray[i] = 1;
			}else{
				correctArray[i] = 0;
			}
		}
		assertTrue(Arrays.equals(dataSet.get(0).getDesiredOutput(), correctArray));
	
	}
	//Makes sure the input array is correct
	@Test
	public void correctInput() {
		ArrayList<int[]> inputList = new ArrayList<int[]>();
		inputList.add(new int[]{12,2,3,4,5,6,7});
		NeuralNetworkDataFormatter dataFormatter = new NeuralNetworkDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(inputList);
		double[] correctInputArray = new double[]{2,3,4,5,6,7};
		assertTrue(Arrays.equals(dataSet.get(0).getInput(), correctInputArray));
	}


}
