package model;

/*
 * Meeting
 * 
 * Author: Even
 * Version: 1.0
 * 
 * Class containing data for a <code>Meeting</code>
 */

import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * Class representing a <code>Meeting</code> with the corresponding 
 * <code>Room</code> <code>Reservation</code>
 * 
 * @author Even
 * @version 1.0
 */
public class Meeting extends Appointment{

	private User owner;
	private Room room;
	private String description;
	private ArrayList<User> participants; 
	
	/**
	 * Constructs a <code>Meeting</code> with the submitted values
	 * @param owner
	 * 			The <code>User</code> that called the meeting
	 * @param room
	 * 			The <code>Room</code> in which the meeting will be held
	 * @param res
	 * 			The <code>Reservation</code> associated with the submitted 
	 * 			<code>Room</code> in the given time range
	 * @param id
	 * 			A unique database meeting ID
	 * @param start
	 * 			The time for the meeting to start
	 * @param end
	 * 			The time for the meeting to end
	 * @param description
	 * 			A textual description of the meeting
	 */
	public Meeting(User owner, Room room, int id, 
			Timestamp start, Timestamp end, String description){
		
		super(id, owner, start, end, description);
		this.owner = owner;
		this.room = room;
	}
	
	public void notifyUser(User usr, Message n){
		usr.addMsg(n);
	}
	
	
}
