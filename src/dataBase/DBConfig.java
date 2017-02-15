package dataBase;

public class DBConfig{
	public static String localhostID = "8889";
	public static String username = "root";
	public static String password = "root";
	public static String mySQLConnectionAddress = "jdbc:mysql://localhost:" + localhostID + "/?user=" + username + "&password=" + password;
	public static String databaseConnectionAddress = "jdbc:mysql://localhost:"+localhostID+"/omnipredictor?user="+ username +"&password=" + password;
}