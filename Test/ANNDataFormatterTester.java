import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.neuroph.core.data.DataSet;

import NeuralNetwork.ANNDataFormatter;

public class ANNDataFormatterTester {

	@Test
	public void toDataSetWorks() {
		ArrayList<int[]> inputList = new ArrayList<int[]>();
		inputList.add(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21});
		ANNDataFormatter dataFormatter = new ANNDataFormatter();
		DataSet dataSet = dataFormatter.toDataSet(inputList);
		assertTrue(true);
	}


}
