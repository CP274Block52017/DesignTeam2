package DataManipulation;
import java.math.BigDecimal;

public class DJObject implements DataObject{
	private java.sql.Date date;
	private BigDecimal openingValue;
	
	public DJObject(java.sql.Date date, BigDecimal openingValue){
		this.date = (date);
		this.openingValue = (openingValue);
	}

	public java.sql.Date getDate() {
		return date;
	}

	public BigDecimal getOpeningValue() {
		return openingValue;
	}
}
