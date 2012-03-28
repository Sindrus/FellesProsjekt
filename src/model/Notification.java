package model;

public class Notification {

	private int id;
	private User owner;
	private int appointmentID;

	private String description;
	
	public Notification(int id, User owner, int appointmentID, String description){
		
		this.id = id;
		this.owner = owner;
		this.appointmentID = appointmentID;
		this.description = description;
		
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
