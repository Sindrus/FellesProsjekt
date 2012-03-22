package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class DBUser {

	/**
	 * Finds all the users currently in the database
	 * @return an <code>ArrayList</code> containing all <code>User</code>s in 
	 * 			the system
	 */
	public static ArrayList<User> getUsersInSystem(){
		
		String sql = "SELECT Brukernavn, Navn FROM Bruker;";
		ArrayList<User> list = new ArrayList<User>();
		try {
			
			ResultSet results = Database.execute(sql);
			while(results.next()){
				String name = results.getString("Navn");
				String username = results.getString("Brukernavn");
				list.add(new User(name, username));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	/**
	 * Get all appointments participated in by the user with the submitted user ID
	 * @param userID
	 * 			A unique database user ID
	 * @return an <code>ArrayList</code> containing all appointments 
	 * participated in by the user with the submitted user ID
	 */
	public static ArrayList<Appointment> getUserAppointments(int userID){
		
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		
		String sql = "SELECT ID FROM Avtale WHERE Avtale.ID IN " +
				"(SELECT Avtale_ID FROM Bruker JOIN Deltaker ON " +
				"Bruker_ID = Bruker.ID WHERE Bruker.ID = " + userID + ") ORDER BY Tid_start;";
		
		try{
			
			ResultSet results = Database.execute(sql);
			while(results.next()){
				
				int id = results.getInt("ID");
				list.add(DBAppointment.getAppointment(id));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return list;
		
	}
	
}
