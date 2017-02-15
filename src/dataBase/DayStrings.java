package dataBase;

/**
 * This class implements DataObject to create a DayStrings object.
 * This object is used to associate a date with all its' headlines 
 * or unique words in our preprocessing for our database and neural network 
 * training, depending on the stage of preprocessing.
 */

public class DayStrings implements DataObject{
	private java.sql.Date date;
	private String[] stringArray;
	
	public DayStrings(java.sql.Date date, String[] stringArray){
		this.date = date;
		this.stringArray = stringArray;
	}

	public String[] getStringArray() {
		return stringArray;
	}
	
	public java.sql.Date getDate(){
		return date;
	}
}
