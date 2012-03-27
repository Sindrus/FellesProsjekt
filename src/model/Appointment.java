package model;

import java.sql.Timestamp;

public class Appointment {
	
	protected int id;
	protected long start;
	protected long end;
	protected String description;
	protected String title;

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
	public Appointment(int id, long start, long end, String title, String description){
		
		this.id = id;
		this.start = start;
		this.end = end;
		this.title = title;
		this.description = description;
		
	}
	
	public void setTitle(String what){
		this.title = what;
	}
	public String getTitle(){
		return title;
	}
	
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
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
