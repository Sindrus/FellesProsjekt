package model;

/*
 * DBAppointment
 * 
 * Author: Even
 * Verision: 1.0
 * 
 * Handles interaction between the database and model layers
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import database.Database;

/**
 * Handles interaction between the database and model layers
 * 
 * @author Even
 * @version 1.0
 * @see Database Database layer
 */
public class DBAppointment {

	
	/**
	 * Get an appointment in database from unique appointment ID
	 * 
	 * @param appointmentID
	 * 				Unique database appointment ID
	 * @return a fully initialized <code>Appointment</code> object.
	 */
	public static Appointment getAppointment(int appointmentID){

		String sql = "SELECT ID, Tid_start, Tid_slutt, Tittel, Beskrivelse FROM Avtale WHERE Avtale.ID = "
			+ appointmentID + ";";
		try{

			ResultSet results = Database.execute(sql);
			while(results.next()){

				int id = results.getInt("ID");
				long start = Long.parseLong(results.getTimestamp("Tid_start").toString());
				long end = Long.parseLong(results.getTimestamp("Tid_slutt").toString());
				String title = results.getString("Tittel");
				String desc = results.getString("Beskrivelse");
				return new Appointment(id, start, end, title, desc);

			}

		} catch(Exception e){
			System.out.println("Nå gikk det til helvete!");
			e.printStackTrace();
		}

		return null;

	}

	
	/**
	 * Add a new appointment to the database
	 * 
	 * @param start
	 * 			The desired time for the appointment to start
	 * @param end
	 * 			The desired time for the appointment to end
	 * @param description
	 * 			A textual description of the appointment
	 * @return a fully initialized <code>Appointment</code> object for the new
	 *  		appointment
	 */
	public static Appointment newAppointment(int userID, long start, long end, String title, String desc){

		String insertAppointment = "INSERT INTO Avtale(Tid_start, Tid_slutt, Tittel, Beskrivelse)" +
		" VALUES (" 
		+ start
		+ ", "
		+ end
		+ ", '"
		+ title
		+ "', '"
		+ desc
		+ "');";

		
		int id = Database.executeUpdate(insertAppointment, true);

		String insertOwner = "INSERT INTO Oppretter_og_Eier(Bruker_ID, Avtale_ID) VALUES("
				+ userID
				+ ", "
				+ id
				+ ");";
		Database.executeUpdate(insertOwner);

		return DBAppointment.getAppointment(id);

	}
	
	/**
	 * Deletes an appointment from database, yielding an <code>int</code>
	 * telling whether the deletion was successful or not
	 * @param id
	 * 			A unique database appointment id
	 * @return a positive <code>int</code> if the deletion was successful;
	 * 			otherwise <code>-1</code>
	 */
	public static int deleteAppointment(int id){
		
		String sql = "DELETE FROM Avtale WHERE ID = "
					+ id
					+ ";";
		int successful = Database.executeUpdate(sql);
		return successful;
		
	}
	
	
	/**
	 * Yields the appointments in the given time range for the given user
	 * @param from
	 * @param to
	 * @return an <code>ArrayList</code> containing the appointments in the 
	 * 			given time range cohering with the given user ID
	 */
	public static ArrayList<Appointment> getAppointmentsInInterval(long from, long to, int userID){
		
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		
		String sql = "SELECT * FROM Avtale WHERE ID IN "
					+ "(SELECT Avtale_ID FROM Deltaker JOIN Bruker ON Bruker.ID = Bruker_ID"
					+ " WHERE Tid_start > "
					+ from
					+ " AND Tid_slutt < "
					+ to
					+ " AND Bruker.ID = "
					+ userID
					+ ") ORDER BY Tid_start;";
		
		try {
			
			ResultSet results = Database.execute(sql);
			while(results.next()){
				
				int id = results.getInt("ID");
				long start = results.getTimestamp("Tid_start").getTime();
				long end = results.getTimestamp("Tid_slutt").getTime();
				String title = results.getString("Tittel");
				String desc = results.getString("Beskrivelse");
				list.add(new Appointment(id, start, end, title, desc));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	/**
	 * Updates the time of the appointment in database corresponding to the 
	 * submitted appointment ID, and returns an <code>int</code> telling 
	 * whether the change was successful or not
	 * 
	 * @param newTimeFrom
	 * 			New start time to be set for the appointment 
	 * @param newTimeTo
	 * 			New end time to be set for the appointment
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return a positive <code>int</code> if the update was successful;
	 * 			otherwise <code>-1</code>
	 */
	public static int changeTimeOfAppointment(long newTimeFrom, long newTimeTo, int appointmentID){
		
		String sql = "UPDATE Avtale SET Tid_start="
					+ newTimeFrom
					+ ", Tid_slutt="
					+ newTimeTo
					+ " WHERE ID = "
					+ appointmentID
					+ ";";
		
		return Database.executeUpdate(sql);
		
	}
	
	/**
	 * Changes the description of the given appointment
	 * 
	 * @param appointmentID
	 * @param newDescription
	 * @return a positive <code>int</code> if the update was successful;
	 * 			otherwise <code>-1</code>
	 */
	public static int changeAppointmentDescription(int appointmentID, String newDescription){
		
		String sql = "UPDATE Avtale set Beskrivelse = "
					+ newDescription
					+ " WHERE ID = "
					+ appointmentID
					+ ";";
		
		return Database.executeUpdate(sql);
		
	}
	
	public static void main(String[] args) {

		
		
	}

}
