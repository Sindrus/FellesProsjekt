package model;

public class Appointment {

	private User owner;
	private Room room;
	private Reservation res;
	private String description;
	
	public Appointment(User owner, Room room, Reservation res, String description ){
		this.owner = owner;
		this.room = room;
		this.res = res;
		this.description = description;
	}
	
	public void notifyUser(User usr, Message n){
		usr.addMsg(n);
	}
	
	
}
