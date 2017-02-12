package preprocessing;

public class WordCount {
	private String word;
	private int count;
	
	public WordCount(String word, int count){
		this.word = word;
		this.count = count;
	}

	public int getWordCount() {
		return count;
	}
	
	public String getWord(){
		return word;
	}
	public void setWordCount(int count) {
		this.count = count;
	}
}
