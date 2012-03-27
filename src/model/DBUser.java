package model;

/*
 * DBUser
 * 
 * Author: Even
 * Version: 1.0
 * 
 * Class handling interaction between database and model layers for the user
 * class
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

/**
 * Class handling interaction between database and model layers for the user
 * class
 * 
 * @author Even
 * @version 1.0
 * @see Database Database layer
 */
public class DBUser {

	/**
	 * Finds all the users currently in the database
	 * @return an <code>ArrayList</code> containing all <code>User</code>s in 
	 * 			the system
	 */
	public static ArrayList<User> getUsersInSystem(){
		
		String sql = "SELECT ID, Brukernavn, Navn FROM Bruker;";
		ArrayList<User> list = new ArrayList<User>();
		try {
			
			ResultSet results = Database.execute(sql);
			while(results.next()){
				int userID = results.getInt("ID");
				String name = results.getString("Navn");
				String username = results.getString("Brukernavn");
				list.add(new User(userID, name, username));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public static User newUser(String name, String username, String password){
		
		String sql = "INSERT INTO Bruker(Navn, Brukernavn, Passord) VALUES('"
					+ name
					+ "', '"
					+ username
					+ "', '"
					+ password
					+ "');";
		
		int userID = Database.executeUpdate(sql, true);
		return DBUser.getUser(userID);
		
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
	
	/**
	 * Fetches a specific user from the database
	 * 
	 * @param userID
	 * 			A unique database user ID
	 * @return A fully initialized <code>User</code> object
	 */
	public static User getUser(int userID){
		
		String sql = "SELECT * FROM Bruker WHERE ID = "
					+ userID
					+ ";";
		
		try {
			ResultSet results = Database.execute(sql);
			if(results.next()){
				String name = results.getString("Navn");
				String username = results.getString("Brukernavn");
				//The user is successfully returned
				return new User(userID, name, username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Something went wrong
		return null;
		
	}
	
}
