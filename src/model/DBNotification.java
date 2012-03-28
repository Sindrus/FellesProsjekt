package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Database;

public class DBNotification {

	
	/**
	 * Fetches a unique database notification ID from a unique database
	 * appointment ID
	 * 
	 * @param appointmentID
	 * @return an <code>int</code> representing the notification in database
	 */
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
	
	/**
	 * Fetches notification corresponding to a user from the database
	 * 
	 * @param userID
	 * 			A unique database notification ID
	 * @return a <code>List</code> containing all notifications for the given
	 * 			user ID
	 */
	public static ArrayList<Notification> getNotifications(int userID){
		
		String sql = "SELECT * FROM Varsel WHERE Varsel.ID IN "
					+ "(SELECT Varsel_ID FROM Deltaker WHERE Bruker_ID = "
					+ userID
					+ " AND Status = 1);";
		
		List<Notification> list = new ArrayList<Notification>();
		try {
			
			ResultSet results = Database.execute(sql);
			while(results.next()){
				User owner = DBUser.getUser(userID);
				int id = results.getInt("ID");
				int appointmentID = results.getInt("Avtale_ID");
				String desc = results.getString("Beskrivelse");
				list.add(new Notification(id, owner, appointmentID, desc));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (ArrayList<Notification>) list;
		
	}
	
	public static void main(String[] args) {
		
		ArrayList<Notification> list = DBNotification.getNotifications(35);
		for(Notification n : list){
			
			System.out.println("Beskrivelse: " + n.getDescription());
			System.out.println("ID: " + n.getId());
			System.out.println("Eier: " + n.getOwner().getName());
			
		}
		
	}
	
}
