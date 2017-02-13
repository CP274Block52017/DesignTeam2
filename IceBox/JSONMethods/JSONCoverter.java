package preprocessing;

import java.util.ArrayList;
import java.util.List;
import org.json.*;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;

public class JSONCoverter {
	public List<DataObject> getJSON (List<DayWordCount> file){
		
		
		List<DataObject> returnSet = new ArrayList<DataObject>();
		JSONObject json = new JSONObject();
		for(int i = 0; i < file.size(); i ++){
			java.sql.Date sqlDate = file.get(0).getDate();
			WordCount [] wordlist = file.get(1).getWordCountArray();
			
			for(int j = 0; j < wordlist.length; j++){
				String word = wordlist[i].getWord();
				int count = wordlist[i].getWordCount();
				json.put(word, count);
				String jsonString = json.toString();
				System.out.println(jsonString);
			}
			String jsonString = json.toString();
			System.out.println(jsonString);
			DateStringObject rowObject = new DateStringObject(sqlDate,jsonString);
			returnSet.add(rowObject);
		}
		return returnSet;
	}
}
