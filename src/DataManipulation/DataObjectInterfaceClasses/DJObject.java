package DataManipulation.DataObjectInterfaceClasses;
import java.math.BigDecimal;

/**
 * This class implements DataObject to create a DJObject.
 * This object is used to associate Dow Jones values with a date in our 
 * preprocessing for our database and expected outputs.
 */
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
