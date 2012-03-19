package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	public static Connection connectionSetup(){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(Config.SQL_JDBC + Config.SQL_DB,
					Config.SQL_USERNAME, Config.SQL_PASSWORD);
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
}
