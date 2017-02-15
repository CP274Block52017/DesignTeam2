package preprocessing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * This class serves to split strings into individual words and is used by
 * the PropositionRemover.
 */

public class StringTokenizer {
	public String[] getTokens(String story) throws FileNotFoundException{
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
}
