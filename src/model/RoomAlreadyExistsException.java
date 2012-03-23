package model;

/**
 * Exception thrown if the user tries to create a room with room number
 * that is already in the database
 * 
 * @author Even
 * @version 1.0
 */
public class RoomAlreadyExistsException extends Exception {

	@Override
	public String toString() {
		return "Romnummeret er opptatt, velg et annet";
	}
	
}
