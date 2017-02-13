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
		int OUTPUT_SIZE = 16;
		DataSet d = new DataSet(set.get(0).length - OUTPUT_SIZE, OUTPUT_SIZE );
		for(int[] currentRow : set) {
			int[] input = Arrays.copyOfRange(currentRow, OUTPUT_SIZE,currentRow.length );
			double[] dbleInput = Arrays.stream(input).asDoubleStream().toArray();
			int[] output = Arrays.copyOfRange(currentRow, 0, OUTPUT_SIZE);
			double[] dbleOutput = Arrays.stream(output).asDoubleStream().toArray();
			d.addRow(dbleInput, dbleOutput);
		}
		return d;
		
		
	}

}