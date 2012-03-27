package gui;

import java.awt.BorderLayout;

import javax.swing.*;

import model.*;

public class AButton extends JButton {

	private int id;
	private User user;
	private long start;
	private long end;
	private String title;
	private Appointment app;
	
	public AButton(Appointment a){
		this.id = a.getId();
		this.start = a.getStart();
		this.end = a.getEnd();
		this.title = a.getTitle();
		this.app = a;
		
		setLayout(new BorderLayout());
		JLabel label1 = new JLabel(title);
		JLabel label2 = new JLabel("Fra: " + start);
		JLabel label3 = new JLabel("Til: " + end);
		add(BorderLayout.NORTH,label1);
		add(BorderLayout.CENTER, label3);
		add(BorderLayout.SOUTH,label2);

	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	public Appointment getAppointment(){
		return this.app;
	}
}
