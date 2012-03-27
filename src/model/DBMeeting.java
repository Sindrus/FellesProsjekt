package model;

/*
 * DBMeeting
 * 
 * Author: Even
 * Version: 1.0
 * 
 * Class handling interaction between database and model layers for the
 * meeting class
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

/**
 * Class handling interaction between database and model layers for the
 * meeting class
 * 
 * @author Even
 * @see Database Database layer
 */
public class DBMeeting {

	/**
	 * Deletes a meeting from the database based on the provided apointment ID 
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return <code>1</code> if the deletion was successful; otherwise
	 * 			<code>-1</code>
	 * @see DBAppointment#deleteAppointment Appointment deletion
	 * @see DBRoom#deleteReservation Reservation deletion
	 */
	public static int deleteMeeting(int appointmentID){
		
		boolean successfulAppointmentDeletion = DBAppointment.deleteAppointment(appointmentID) > 0;
		boolean successfulReservationDeletion = DBRoom.deleteReservation(appointmentID) > 0;
		return (successfulAppointmentDeletion && successfulReservationDeletion ? 1 : -1);
		
	}
	
	/**
	 * Fetches a specific meeting from the database
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return An initialized <code>Meeting</code> object
	 */
	public static Meeting getMeeting(int appointmentID){
		
		ArrayList<User> invited = DBMeeting.getInvitedUsers(appointmentID);
		Appointment appointment = DBAppointment.getAppointment(appointmentID);
		
		String fetchReservation = "SELECT Romnr FROM Reservasjon WHERE Avtale_ID = "
					+ appointmentID
					+ ";";
		
		String fetchOwner = "SELECT * FROM Bruker JOIN Oppretter_og_Eier ON Bruker.ID = Bruker_ID"
						+ " WHERE Avtale_ID = "
						+ appointment.getId()
						+ ";";
		
		User owner = null;
		try{
			ResultSet results = Database.execute(fetchOwner);
			if(results.next()){
				owner = DBUser.getUser(results.getInt("Bruker_ID"));
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		try{
			
			ResultSet results = Database.execute(fetchReservation);
			if(results.next()){
				int roomNumber = results.getInt("Romnr");
				//The meeting is successfully returned
				return new Meeting(owner,
						DBRoom.getRoom(roomNumber), appointment.getId(), appointment.getStart(),
						appointment.getEnd(), appointment.getTitle(), appointment.getDescription());
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		//Something went wrong
		return null;
		
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
	 * @return a fully initialized Meeting object
	 */
	public static Meeting newMeeting(User owner, int appointmentID, int roomNumber, long from, long to){
		
		String sql = "INSERT INTO Oppretter_og_Eier VALUES "
					+ owner.getId()
					+ ", "
					+ appointmentID
					+ ";";
		
		Database.executeUpdate(sql);
		
		try {
			int roomInsertionID = DBRoom.reserveRoom(roomNumber, appointmentID, from, to);
			//The meeting is successfully returned
			return DBMeeting.getMeeting(appointmentID);
		} catch (ReservationAlreadyMadeException e) {
			e.printStackTrace();
			//Something went wrong
			return null;
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
	 * @return a fully initialized meeting object
	 */
	public static Meeting newMeeting(User owner, int roomNumber, long from, long to,
			 String title, String description, ArrayList<User> participants){

		int appointmentID = DBAppointment.newAppointment(owner.getId(), from, to, title, description).getId();

		String makeMeetingRef = "INSERT INTO Oppretter_og_Eier(Bruker_ID, Avtale_ID) VALUES( "
					+ owner.getId()
					+ ", "
					+ appointmentID
					+ ");";
		Database.executeUpdate(makeMeetingRef);
		
		String makeNotificationRef = "INSERT INTO Varsel(Avtale_ID, Beskrivelse) VALUES ("
									+ appointmentID
									+ ", '"
									+ title
									+ "');";
		Database.executeUpdate(makeNotificationRef);

		for(User user : participants){
			String addParticipants = "INSERT INTO Deltaker(Avtale_ID, Bruker_ID, Varsel_ID) VALUES ("
									+ appointmentID
									+ ", "
									+ user.getId()
									+ ", "
									+ DBNotification.getNotificationID(appointmentID)
									+ ");";
			Database.executeUpdate(addParticipants);
		}
		
		try {
			int roomInsertionId = DBRoom.reserveRoom(roomNumber, appointmentID, from, to);
			//The meeting is successfully returned
			return DBMeeting.getMeeting(appointmentID);
		} catch (ReservationAlreadyMadeException e) {
			e.printStackTrace();
			//Something went wrong
			return null;
		}
	}
	
	/**
	 * Fetches all users that are invited to the given meeting
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return an <code>ArrayList</code> containing the users invited to the
	 * 			meeting specified by the appointment ID
	 */
	public static ArrayList<User> getInvitedUsers(int appointmentID){
		
		ArrayList<User> list = new ArrayList<User>();
		String sql = "SELECT * FROM Bruker WHERE ID IN "
					+ "(SELECT Bruker_ID FROM Bruker JOIN Deltaker ON "
					+ "Bruker.ID = Bruker_ID WHERE Avtale_ID = "
					+ appointmentID
					+ ") ORDER BY Navn ASC;";
		
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
	
	/**
	 * Changes the participants of the given meeting
	 * 
	 * @param appointmentID
	 * @param oldParticipants
	 * 			An <code>ArrayList</code> containing the participants that used
	 * 			to be partaking in the meeting
	 * @param newParticipants
	 * 			An <code>ArrayList</code> containing the participants that are 
	 * 			now partaking in the meeting
	 * @return <code>1</code> if the update was successful; otherwise <code>-1</code>
	 */
	public static int changeParticipants(int appointmentID, ArrayList<User> oldParticipants, ArrayList<User> newParticipants){
		
		int successfulDeletion = -1;
		for(User user : oldParticipants){
			String delete = "DELETE FROM Deltaker WHERE Avtale_ID = "
							+ appointmentID
							+ " AND Bruker_ID = "
							+ user.getId()
							+ ";";
			successfulDeletion = Database.executeUpdate(delete);
		}
		
		int successfulInsertion = -1;
		for(User user : newParticipants){
			String insert = "INSERT INTO Deltaker(Avtale_ID, Bruker_ID, Varsel_ID) VALUES("
							+ appointmentID
							+ ", "
							+ user.getId()
							+ ", "
							+ DBNotification.getNotificationID(appointmentID)
							+ ");";
			successfulInsertion = Database.executeUpdate(insert);
		}
		
		return (successfulDeletion>-1 && successfulInsertion>0 ? 1 : -1);
		
	}
	
	public static int inviteParticipants(int appointmentID, ArrayList<User> participants){
		
		for(User user : participants){
			
			String sql = "INSERT INTO Deltaker(Avtale_ID, Bruker_ID, Varsel_ID) VALUES ("
						+ appointmentID
						+ ", "
						+ user.getId()
						+ ", "
						+ DBNotification.getNotificationID(appointmentID)
						+ ");";
			return Database.executeUpdate(sql);
			
		}
		return -1;
		
	}
	
	public static void main(String[] args) {
		
		
		
	}
	
}
