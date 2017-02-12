package preprocessing;

public class DayWordCount {
	private java.sql.Date date;
	private WordCount[] wordCountArray;
	
	public DayWordCount(java.sql.Date date, WordCount[] wordCountArray){
		this.date = date;
		this.wordCountArray = wordCountArray;
	}

	public WordCount[] getWordCountArray() {
		return wordCountArray;
	}
	
	public java.sql.Date getDate(){
		return date;
	}
	
	public void setWordCountArray(WordCount[] wordCountArray) {
		this.wordCountArray = wordCountArray;
	}
	
}
