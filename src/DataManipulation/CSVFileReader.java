package DataManipulation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * This class serves to read CSV Files and converts the data into a list of string arrays.
 * The readFile method is currently used to read in the DJIA and Reddit News Data sets in /Data
 *
 */
public class CSVFileReader {
	

	public List<String[]> readFile(String file){
		FileReader fileReader; 
		List<String[]> dataList = null;
		
		try{
			fileReader = new FileReader(file);
			CSVReader reader = new CSVReader(fileReader);
			dataList = reader.readAll();
			reader.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e ){}
		dataList.remove(0); //fix for loops
		return dataList;
	}
}
