package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

import database.Database;

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
	private Appointment(int id, Timestamp start, Timestamp end, String description){
		
		this.id = id;
		this.start = start;
		this.end = end;
		this.description = description;
		
	}
	
	/**
	 * Get an appointment in database from unique appointment ID
	 * 
	 * @param appointmentID
	 * 				Unique database appointment ID
	 * @return a fully initialized <code>Appointment</code> object.
	 */
	public static Appointment getAppointment(int appointmentID){
		
		String sql = "SELECT ID, Tid_start, Tid_slutt, Beskrivelse FROM Avtale WHERE Avtale.ID = "
			+ appointmentID + ";";
		try{
			
			ResultSet results = Database.execute(sql);
			while(results.next()){
				
				int id = results.getInt("ID");
				Timestamp start = results.getTimestamp("Tid_start");
				Timestamp end = results.getTimestamp("Tid_slutt");
				String desc = results.getString("Beskrivelse");
				return new Appointment(id, start, end, desc);
				
			}
			
		} catch(Exception e){
			System.out.println("Nå gikk det til helvete!");
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * Add a new appointment to the database
	 * 
	 * @param start
	 * 			The desired time for the appointment to start
	 * @param end
	 * 			The desired time for the appointment to end
	 * @param description
	 * 			A textual description of the appointment
	 * @return a fully initialized <code>Appointment</code> object for the new
	 *  		appointment
	 */
	public static Appointment newAppointment(Date start, Date end, String description){
		
		String sql = "INSERT INTO Avtale(ID, Tid_start, Tid_slutt, Beskrivelse)" +
				" VALUES (" 
				+ start
				+ ", "
				+ end
				+ ", '"
				+ description
				+ "');";
		
		int id = Database.executeUpdate(sql, true);
		
		return Appointment.getAppointment(id);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
}
