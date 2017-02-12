package preprocessing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class PrepositionRemover {
	public List<DayStrings> removePrepositions(List<DayStrings> dayStoryList) throws FileNotFoundException{
		List<DayStrings> dayStrings = convertToTokens(dayStoryList);
		List<DayStrings> returnList = new ArrayList<DayStrings>();
		for(DayStrings i : dayStrings){
			String[] tokenArray = i.getStringArray();
			String[] tagsArray = getTags(tokenArray);
			String[] cleanedArray = cleanArray(tokenArray,tagsArray);
			DayStrings newDayStrings = new DayStrings(i.getDate(),cleanedArray);
			returnList.add(newDayStrings);
		}
		return returnList;
	}
	
	private String[] cleanArray(String[] tokens, String[] tags){
		ArrayList<String> cleanStrings = new ArrayList<String>();
		for(int i=0;i<tags.length;i++){
			if(verbOrNoun(tags[i])){
				cleanStrings.add(tokens[i].toLowerCase());
			}
		}
		String[] returnArray = new String[cleanStrings.size()];
		returnArray = cleanStrings.toArray(returnArray);
		return returnArray;
	}
	
	private boolean verbOrNoun(String tag){
		if(tag.contains("NN")||tag.contains("VB")){
			return true;
		}else{
			return false;
		}
	}
	
	private List<DayStrings> convertToTokens(List<DayStrings> dayStoryList) throws FileNotFoundException{
		StringTokenizer tokenizer = new StringTokenizer();
		List<DayStrings> returnList = new ArrayList<DayStrings>();
		for(DayStrings i : dayStoryList){
			String allStories = concatStoriesFromDay(i);
			String[] tokens = tokenizer.getTokens(allStories);
			DayStrings newDayStrings = new DayStrings(i.getDate(),tokens);
			returnList.add(newDayStrings);
		}
		return returnList;
	}
	
	private String concatStoriesFromDay(DayStrings dayStory){
		String[] stories = dayStory.getStringArray();
		String allStories = "";
		for(String j : stories){
			allStories+=j+" ";
		}
		return allStories;
	}
	
	private String[] getTags(String[] tokens){
		InputStream modelIn = null;
		String[] tags = null;

		try {
		  modelIn = new FileInputStream("Data/en-pos-maxent.bin");
		  POSModel model = new POSModel(modelIn);
		  POSTaggerME tagger = new POSTaggerME(model);
		  tags = tagger.tag(tokens);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
		return tags;
	}
}