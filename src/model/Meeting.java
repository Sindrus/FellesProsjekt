package model;

import java.util.ArrayList;

public class Meeting extends Appointment{

	private User owner;
	private Room room;
	private Reservation res;
	private String description;
	private ArrayList<User> participants; 
	
	public Meeting(User owner, Room room, Reservation res, String description){
		this.owner = owner;
		this.room = room;
		this.res = res;
	}
	
	public void notifyUser(User usr, Message n){
		usr.addMsg(n);
	}
	
	
}
