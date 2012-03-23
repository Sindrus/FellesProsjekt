package model;

/*
 * DBRoom
 * 
 * Author: Even
 * Version: 1.0
 * 
 * Class for handling communication between database an model layers for the 
 * Room class
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

/**
 * Class for handling communication between database and model layers for the
 * <code>Room</code> class
 *  
 * @author Even
 * @version 1.0
 * @see Database Database layer
 */
public class DBRoom {

	/**
	 * Yields all the available rooms equal to or larger than the submitted
	 * number of participants in the submitted time range
	 * 
	 * @param start
	 * 			A <code>long</code> representing the time for the meeting to 
	 * 			start
	 * @param end
	 * 			A <code>long</code> representing the time for the meeting to
	 * 			end
	 * @param numberOfParticipants
	 * 
	 * @throws NoAvailableRoomsException
	 * @return an <code>ArrayList</code> containing all available rooms of the
	 * 			given size in the given time range
	 */
	public static ArrayList<Room> getAvailibleRooms(int numberOfParticipants, long start, long end) throws NoAvailableRoomsException{

		ArrayList<Room> list = new ArrayList<Room>();
		String sql = "SELECT * FROM Rom WHERE Romnr NOT IN"
			+ " (SELECT Rom.Romnr FROM Rom JOIN Reservasjon "
			+ "ON Rom.Romnr = Reservasjon.Romnr WHERE "
			+ "(Tid_start <= "
			+ start
			+ " AND Tid_slutt >= "
			+ start
			+ ") OR (Tid_start <= "
			+ end
			+ " AND Tid_slutt >= "
			+ end
			+ ")) AND Størrelse >= " +
			+ numberOfParticipants
			+ " ORDER BY Størrelse;";

		System.out.println(sql);
		try {
			ResultSet results = Database.execute(sql);
			while(results.next()){
				int roomNo = results.getInt("Romnr");
				int size = results.getInt("Størrelse");
				list.add(new Room(roomNo, size));
			}
			if(list.isEmpty()){
				throw new NoAvailableRoomsException();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * Checks if the provided appointment ID already has a room reservation
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return <code>true</code> if the appointment has a reservation; 
	 * 			otherwise <code>false</code>
	 */
	private static boolean reservationInDatabase(int appointmentID){
		
		String sql = "SELECT * FROM Reservasjon WHERE Avtale_ID = "
					+ appointmentID
					+ ";";
		
		try{
			ResultSet results = Database.execute(sql);
			while(results.next()){
				if(results.getInt("Avtale_ID") == appointmentID){
					System.out.println("Shite");
					return true;
				}
			} 
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	/**
	 * Creates a room reservation in the database containing the provided info
	 * 
	 * @param roomNumber
	 * 			The number of the room desired to reserve
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @param from
	 * 			A <code>long</code> representing the time from which the room 
	 * 			is to be reserved
	 * @param to
	 * 			A <code>long</code> representing the time to which the room is
	 * 			to be reserved
	 * @return a positive <code>int</code> if the reservation was successful;
	 * 			otherwise <code>-1</code>
	 * @throws ReservationAlreadyMadeException 
	 */
	public static int reserveRoom(int roomNumber, int appointmentID, long from, long to) throws ReservationAlreadyMadeException{

		if(reservationInDatabase(appointmentID)){
			throw new ReservationAlreadyMadeException();
		}
		
		String sql = "INSERT INTO Reservasjon(Avtale_ID, Romnr, Tid_start, "
			+ "Tid_slutt) VALUES ("
			+ appointmentID
			+ ", "
			+ roomNumber
			+ ", "
			+ from
			+ ", "
			+ to
			+ ");";

		return Database.executeUpdate(sql);

	}
	
	/**
	 * Deletes a room reservation from the database based on the provided
	 * appointment ID
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @return a positive <code>int</code> if the deletion was successful;
	 * 			otherwise <code>-1</code>
	 */
	public static int deleteReservation(int appointmentID){
		
		String sql = "DELETE FROM Reservasjon WHERE Avtale_ID = "
					+ appointmentID
					+ ";";
		
		return Database.executeUpdate(sql);
		
	}
	
	/**
	 * Deletes a room reservation from the database based on the provided room
	 * number and time range
	 * 
	 * @param roomNumber
	 * @param from
	 * 			a <code>long</code> representing the time from which the room
	 * 			is reserved
	 * @param to
	 * 			a <code>long</code> representing the time to which the room
	 * 			is reserved
	 * @return a positive <code>int</code> if the deletion was successful;
	 * 			otherwise <code>-1</code> 
	 */
	public static int deleteReservation(int roomNumber, long from, long to){
		
		String sql = "DELETE FROM Reservasjon WHERE Romnr = "
					+ roomNumber
					+ "AND Tid_start = "
					+ from
					+ "AND Tid_slutt = "
					+ to
					+ ";";
		
		return Database.executeUpdate(sql);
		
	}

	/**
	 * Checks whether the provided room number is already in the database
	 * 
	 * @param roomNumber
	 * @return <code>true</code> if the provided room number exists in database
	 * 			; otherwise <code>false</code>
	 */
	private static boolean roomInDatabase(int roomNumber){

		String sql = "SELECT Romnr FROM Rom";
		try{

			ResultSet results = Database.execute(sql);
			while(results.next()){
				int dbroom = results.getInt("Romnr");
				if(dbroom == roomNumber){
					return true;
				}
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Creates a new room in the database with the desired room number and size
	 * 
	 * @param roomNumber
	 * @param size
	 * @return 
	 * @throws RoomAlreadyExistsException
	 */
	public static int addRoom(int roomNumber, int size) throws RoomAlreadyExistsException {

		if(roomInDatabase(roomNumber)){
			throw new RoomAlreadyExistsException();
		}

		String sql = "INSERT INTO Room(Romnr, Størrelse) VALUES("
			+ roomNumber
			+ ", "
			+ size
			+ ");";
		return Database.executeUpdate(sql);

	}
	
	/**
	 * Changes the room reservation for the given appointment
	 * 
	 * @param appointmentID
	 * 			A unique database appointment ID
	 * @param newRoomNumber
	 * 			An <code>int</code> representing the room the reservation shall
	 * 			now contain
	 * @return A positive <code>int</code> if the update was successful;
	 * 			otherwise <code>-1</code>
	 */
	public static int changeReservation(int appointmentID, int newRoomNumber){
		
		String sql = "UPDATE Reservasjon SET Romnr = "
					+ newRoomNumber
					+ " WHERE Avtale_ID = "
					+ appointmentID
					+ ";";
		
		return Database.executeUpdate(sql);
		
	}
	
	/**
	 * Fetches a specific room from the database
	 * 
	 * @param roomNumber
	 * 			A unique database room ID
	 * @return a fully initialized <code>Room</code> object
	 */
	public static Room getRoom(int roomNumber){
		
		String sql = "SELECT * FROM Rom WHERE Romnr = "
					+ roomNumber
					+ ";";
		try {
			ResultSet results = Database.execute(sql);
			if(results.next()){
				//The room is successgfully returned
				return new Room(results.getInt("Romnr"), results.getInt("Størrelse"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Something went wrong
		return null;
	}

}
