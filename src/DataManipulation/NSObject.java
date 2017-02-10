package DataManipulation;

public class NSObject implements DataObject{
	private java.sql.Date date;
	private String headline;
	
	public NSObject(java.sql.Date date, String headline){
		this.date = date;
		this.headline = headline;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public String getHeadline() {
		return headline;
	}
}
