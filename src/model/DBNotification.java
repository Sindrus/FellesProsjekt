package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public class DBNotification {

	public static int getNotificationID(int appointmentID){
		
		String sql = "SELECT * FROM Varsel WHERE Avtale_ID = "
				    + appointmentID
				    + ";";
		try {
			ResultSet results = Database.execute(sql);
			if(results.next()){
				return results.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	
}
