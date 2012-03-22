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
 *
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

		String sql = "SELECT ID, Tid_start, Tid_slutt, Beskrivelse FROM Avtale WHERE Avtale.ID = "
			+ appointmentID + ";";
		try{

			ResultSet results = Database.execute(sql);
			while(results.next()){

				int id = results.getInt("ID");
				Timestamp start = results.getTimestamp("Tid_start");
				Timestamp end = results.getTimestamp("Tid_slutt");
				String desc = results.getString("Beskrivelse");
				return new Appointment(id, start, end, desc);

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
	public static Appointment newAppointment(Timestamp start, Timestamp end, String desc){

		String sql = "INSERT INTO Avtale(ID, Tid_start, Tid_slutt, Beskrivelse)" +
		" VALUES (" 
		+ start
		+ ", "
		+ end
		+ ", '"
		+ desc
		+ "');";

		int id = Database.executeUpdate(sql, true);

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
				Timestamp start = results.getTimestamp("Tid_start");
				Timestamp end = results.getTimestamp("Tid_slutt");
				String desc = results.getString("Beskrivelse");
				list.add(new Appointment(id, start, end, desc));
				
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

}
