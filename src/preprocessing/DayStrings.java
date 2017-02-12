package preprocessing;

public class DayStrings {
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
