package DataManipulation.DataObjectInterfaceClasses;

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
