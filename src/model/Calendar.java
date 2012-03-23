package model;

import java.util.ArrayList;

public class Calendar {
	
	private User owner;
	public static ArrayList appointments;
	
	public Calendar(User owner){
		this.owner = owner;
		appointments = new ArrayList();
	}
	
	public void addAppointment(Meeting a){
		appointments.add(a);
	}
	
	public static ArrayList getAppointments(){
		return appointments;
	}
	
	public User getOwner(){
		return owner;
	}
}
