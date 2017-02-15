package NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.data.norm.MaxNormalizer;
import org.neuroph.util.data.norm.Normalizer;

public class NeuralNetworkDataFormatter {
	
	//Output is 1 item (DJIA)
	//Binary Size is binary representation of DJIA put into output
	private final int OUTPUT_SIZE = 1;
	private final int BINARY_SIZE = 16;

	//'set' input is (DJIA,Month,Day,Year,word counts.........) for each day. 
	//Takes in 'set' and puts it into DataSet object for ANN. 
	//Makes input Month, Day, Year, word counts and output the DJIA in binary form in array of 16 length.
	public DataSet toDataSet(List<int[]> set) {
		DataSet d = new DataSet(set.get(0).length - OUTPUT_SIZE, OUTPUT_SIZE * BINARY_SIZE);
		for(int[] currentRow : set) {
			int[] input = Arrays.copyOfRange(currentRow, 1,currentRow.length );
			double[] dbleInput = Arrays.stream(input).asDoubleStream().toArray();
			double[] output = outputToBinary(currentRow[0]);
			d.addRow(dbleInput, output);
		}
		return d;
	}
	//takes in DJIA and puts it into binary form stored in a double array of 16 length
	private double[] outputToBinary(int nonBinary ) {
		String binary = Integer.toBinaryString(nonBinary);
		double[] output = new double[16];
		for(int i = 0; i < binary.length(); i++) {
			output[i+(BINARY_SIZE-binary.length())] = Character.getNumericValue(binary.charAt(i));
		}
		//fills in zeros for remainder 16 length place array to make sure it's the proper input size.
		for(int j = 0;j<BINARY_SIZE-binary.length();j++){
			output[j] = 0;
		}
		return output;
	}
	
	//Specify percentages of data used for training and test DataSets, respectively, return array containing both	
		public DataSet[] getTrainingandTest(DataSet dataSet, int TrainingSize, int TestSize) {
			dataSet.shuffle(); //Shuffles data to ensure that the test and training data vary. 
			DataSet[] trainingAndTestSet = dataSet.createTrainingAndTestSubsets(70, 30);
			return trainingAndTestSet;
		}
	

}