package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.*;

import util.DateHelpers;

import model.*;

public class AButton extends JButton {

	private int id;
	private User user;
	private long start;
	private long end;
	private String title;
	private Appointment app;
	
	public AButton(Appointment a){
		
		setBackground(GConfig.APPBUTONCOLOR);
		
		this.id = a.getId();
		this.start = a.getStart();
		this.end = a.getEnd();
		this.title = a.getTitle();
		this.app = a;
		
		HashMap<String, Integer> f = DateHelpers.convertFromTimestamp(start);
		HashMap<String, Integer> t = DateHelpers.convertFromTimestamp(end);
		
		String fh = String.valueOf(f.get("hour"));
		String th = String.valueOf(t.get("hour"));
		String fm = String.valueOf(f.get("minute"));
		String tm = String.valueOf(t.get("minute"));
		
		if(Integer.parseInt(fh) <10) fh = "0" + fh;
		if(Integer.parseInt(th) <10) th = "0" + th;
		if(Integer.parseInt(fm) <10) fm = "0" + fm;
		if(Integer.parseInt(tm) <10) tm = "0" + tm;
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		JLabel label1 = new JLabel(title);
		JLabel label2 = new JLabel("Fra: " + fh + ":" + fm);
		JLabel label3 = new JLabel("Til: " + th + ":" + tm);
		JLabel label4 = new JLabel(app.getDescription());
		
		g.gridy = 0;
		add(label1,g);
		g.gridy = 1;
		add(label2,g);
		g.gridy = 2;
		add(label3,g);
		g.gridy = 3;
		add(label4,g);
		
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
