package preprocessing;

import java.util.ArrayList;
import java.util.List;
import org.json.*;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;

public class JSONCoverter {
	public List<DataObject> getJSON (List<DayWordCount> file){
		/*
		 * 
		 * 		JSONObject json = new JSONObject();
		json.put("name", "student");

		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("information", "test");
		item.put("id", 3);
		item.put("name", "course1");
		array.put(item);

		json.put("course", array);

		message = json.toString();
		
		// message
		// {"course":[{"id":3,"information":"test","name":"course1"}],"name":"student"}*/
		List<DataObject> returnSet = new ArrayList<DataObject>();

		for(int i = 0; i < file.size(); i ++){
			java.sql.Date sqlDate = file.get(0).getDate();
			WordCount [] wordlist = file.get(1).getWordCountArray();
			
			JSONObject json = new JSONObject();
			for(int j = 0; j < wordlist.length; j++){
				String word = wordlist[i].getWord();
				int count = wordlist[i].getWordCount();
				json.put(word, count);
			}
			String jsonString = json.toString();
			DateStringObject rowObject = new DateStringObject(sqlDate,jsonString);
			returnSet.add(rowObject);
		}
		return returnSet;
	}
}
