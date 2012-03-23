package model;

/**
 * Exception thrown if the user tries to make a room reservation for a meeting
 * that already has a reserved room
 * 
 * @author Even
 * @version 1.0
 */
public class ReservationAlreadyMadeException extends Exception {

	@Override
	public String toString() {
		return "Avtalen har allerede en romreservasjon";
	}
	
}
