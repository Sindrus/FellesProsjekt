package model;

import java.sql.Timestamp;

public class Appointment {
	
	private int id;
	private Timestamp start;
	private Timestamp end;
	private String description;
	
	
	/**
	 * Constructs an <code>Appointment</code> object with the submitted 
	 * parameters
	 * @param id
	 * 			Unique database appointment ID
	 * @param start
	 * 			The time for the appointment to start
	 * @param end
	 *			The time for the appointment to end
	 * @param description
	 * 			A textual description of the appointment
	 */
	public Appointment(int id, Timestamp start, Timestamp end, String description){
		
		this.id = id;
		this.start = start;
		this.end = end;
		this.description = description;
		
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
}
