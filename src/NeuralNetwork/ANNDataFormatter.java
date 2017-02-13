package NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.data.norm.MaxNormalizer;
import org.neuroph.util.data.norm.Normalizer;

public class ANNDataFormatter {
	
	public ANNDataFormatter() {}
	
	public DataSet toDataSet(ArrayList<int[]> set) {
		int OUTPUT_SIZE = 1;
		int BINARY_SIZE = 16;
		DataSet d = new DataSet(set.get(0).length - OUTPUT_SIZE, OUTPUT_SIZE * BINARY_SIZE);
		for(int[] currentRow : set) {
			int[] input = Arrays.copyOfRange(currentRow, 1,currentRow.length );
			double[] dbleInput = Arrays.stream(input).asDoubleStream().toArray();
			double[] output = outputToBinary(currentRow[0]);
			d.addRow(dbleInput, output);
		}
		return d;
	}
	
	private double[] outputToBinary(int nonBinary ) {
		String binary = Integer.toBinaryString(nonBinary);
		double[] output = new double[16];
		for(int i = 0; i < binary.length(); i++) {
			output[i+(16-binary.length())] = Character.getNumericValue(binary.charAt(i));
		}
		for(int j = 0;j<16-binary.length();j++){
			output[j] = 0;
		}
		return output;
	}
	

}