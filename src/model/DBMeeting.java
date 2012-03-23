package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public class DBMeeting {

	/**
	 * Deletes a meeting from the database based on the provided apointment ID 
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return <code>1</code> if the deletion was successful; otherwise
	 * 			<code>-1</code>
	 */
	public static int deleteMeeting(int appointmentID){
		
		boolean successfulAppointmentDeletion = DBAppointment.deleteAppointment(appointmentID) > 0;
		boolean successfulReservationDeletion = DBRoom.deleteReservation(appointmentID) > 0;
		return (successfulAppointmentDeletion && successfulReservationDeletion ? 1 : -1);
		
	}
	
	/**
	 * Deletes a meeting from the database based on the provided room number
	 * and time range
	 * 
	 * @param roomNumber
	 * 			The room for which the reservation applies
	 * @param from
	 * 			The time from which the reservation is made
	 * @param to
	 * 			The time to which the reservation is made
	 * @return <code>1</code> if the deletion was successful; otherwise
	 * 			<code>-1</code>
	 */
	public static int deleteMeeting(int roomNumber, long from, long to){
		
		String getId = "SELECT Avtale_ID From Reservasjon WHERE Romnr = "
						+ roomNumber
						+ " AND Tid_start = "
						+ from
						+ " AND Tid_slutt = "
						+ to
						+ ";";
		int appointmentID = -1;
		try {
			ResultSet results = Database.execute(getId);
			if(results.next()){
				appointmentID = results.getInt("Avtale_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return deleteMeeting(appointmentID);
		
	}
	
	/**
	 * Creates an appointment and a coherent room reservation
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @param roomNumber
	 * 			The room number in which the meeting will take place
	 * @param from
	 * 			a <code>long</code> representing the time from which the
	 * 			reservation shall apply
	 * @param to
	 * 			a <code>long</code> representing the time to which the
	 * 			reservation shall apply
	 * @return a positive <code>int</code> if the meeting was created 
	 * 			successfully; otherwise <code>-1</code>
	 */
	public static int createMeeting(int appointmentID, int roomNumber, long from, long to){
		
		try {
			return DBRoom.reserveRoom(roomNumber, appointmentID, from, to);
		} catch (ReservationAlreadyMadeException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Creates an appointment and a coherent room reservation
	 * 
	 * @param roomNumber
	 * 			The room number in which the meeting will take place
	 * @param from
	 * 			a <code>long</code> representing the time from which the
	 * 			reservation shall apply
	 * @param to
	 * 			a <code>long</code> representing the time to which the
	 * 			reservation shall apply
	 * @param description
	 * 			A textual description of the meeting
	 * @return a positive <code>int</code> if the meeting was created 
	 * 			successfully; otherwise <code>-1</code>
	 */
	public static int createMeeting(int roomNumber, long from, long to,
			 String description){

		int appointmentID = DBAppointment.newAppointment(from, to, description).getId();

		try {
			return DBRoom.reserveRoom(roomNumber, appointmentID, from, to);
		} catch (ReservationAlreadyMadeException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}
