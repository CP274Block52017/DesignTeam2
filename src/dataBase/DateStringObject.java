package dataBase;

/**
 * This class implements DataObject to create a DateStringObject.
 * This object is used to associate news stories with a date in our preprocessing 
 * for our database and neural network training.
 *
 */
public class DateStringObject implements DataObject{
	private java.sql.Date date;
	private String string;
	
	public DateStringObject(java.sql.Date date, String string){
		this.date = date;
		this.string = string;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public String getString() {
		return string;
	}
}
