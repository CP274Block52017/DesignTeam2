package preprocessing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class RemovePrepositionsStrategy implements StoryProcessingStrategy{
	
	@Override
	public List<DayStrings> processStories(List<DayStrings> dayStoryList) throws FileNotFoundException{
		List<DayStrings> returnList = new ArrayList<DayStrings>();
		for(int a=0;a<dayStoryList.size();a++){
			List<String> tokens = new ArrayList<String>();
			List<String> tags = new ArrayList<String>();
			String[] stories = dayStoryList.get(a).getStringArray();
			for(int i=0;i<stories.length;i++){
				String story = stories[i];
				String[] tokensArray = getTokens(story);
				String[] tagsArray = getTags(tokensArray);
				for(int j =0;j<tokensArray.length;j++){
					tokens.add(tokensArray[j]);
					tags.add(tagsArray[j]);
				}
			}
			for(int i=0;i<tags.size();i++){
				if(!(tags.get(i).contains("NN")||tags.get(i).contains("VB"))){
					tokens.remove(i);
					tags.remove(i);
					i--;
				}
			}
			System.out.println(Arrays.toString(tokens.toArray()));
			//System.out.println(Arrays.toString(tags.toArray()));
			String[] processedList = new String[tokens.size()];
			for(int i=0;i<tokens.size();i++){
				processedList[i] = tokens.get(i);
			}
			java.sql.Date date = dayStoryList.get(a).getDate();
			DayStrings dayStrings = new DayStrings(date,processedList);
			returnList.add(dayStrings);
		}
		
		return returnList;
	}
	
	private String[] getTokens(String story) throws FileNotFoundException{
		String[] returnTokens = null;
		InputStream modelIn = new FileInputStream("Data/en-token.bin");

		try {
		  TokenizerModel model = new TokenizerModel(modelIn);
		  Tokenizer tokenizer = new TokenizerME(model);
		  returnTokens = tokenizer.tokenize(story);
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
		return returnTokens;
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
		  // Model loading failed, handle the error
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
