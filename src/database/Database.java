package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static ResultSet execute(String sql) throws SQLException{
		Connection conn = connectionSetup();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	
}
