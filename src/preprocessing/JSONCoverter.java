package preprocessing;

import java.util.ArrayList;
import java.util.List;
import org.json.*;

import DataManipulation.DataObjectInterfaceClasses.DataObject;
import DataManipulation.DataObjectInterfaceClasses.DateStringObject;

public class JSONCoverter {
	public List<DataObject> getJSON (List<DayWordCount> file){
		
		  
 		JSONObject json1 = new JSONObject();
 		json1.put("name", "student");
 		json1.put("name", "student2");
		//for(int j = 0; j < 4; j++){
			//json1.put("name", "student");
		//}
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("information", "test");
		item.put("id", 3);
		item.put("name", "course1");
		array.put(item);

		json1.put("course", array);

		String message = json1.toString();
		System.out.println(message);
		
		// message
		// {"course":[{"id":3,"information":"test","name":"course1"}],"name":"student"}*/
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
