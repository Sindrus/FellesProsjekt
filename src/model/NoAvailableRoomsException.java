package model;

/**
 * Exception thrown if there are no rooms available
 * 
 * @author Even
 * @version 1.0
 */
public class NoAvailableRoomsException extends Exception{

	@Override
	public String toString() {

		return "Det var ingen ledige rom til den størrelsen i det gitte tidsrommet";

	}
	
}
