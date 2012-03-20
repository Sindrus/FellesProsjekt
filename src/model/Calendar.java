package model;

import java.util.ArrayList;

public class Calendar {
	
	private User owner;
	private ArrayList appointments;
	
	public Calendar(User owner){
		this.owner = owner;
		appointments = new ArrayList();
	}
	
	public void addAppointment(Appointment a){
		appointments.add(a);
	}
	
	public ArrayList getAppointments(){
		return appointments;
	}
	
	public User getOwner(){
		return owner;
	}
}
