package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Collections;

import java.sql.Timestamp;

import model.Appointment;
import model.DBAppointment;
import model.DBMeeting;
import model.Meeting;
import model.User;

import util.ChangeType;
import util.GUIListener;
import util.GUIListenerSupport;

/**
 * 
 * @author sindre
 * 
 * Inneholder paneler for ny avtale og nytt møte
 * NewAppointmentPanel
 * NewMeetingPanel
 * 
 * 
 */

public class NewPanel extends JPanel implements GUIListener{
	
	NewMeetingPanel newMeetingPanel;
	NewAppointmentPanel newAppointmentPanel;
	private boolean isMeetingPanel;
	private boolean isMeeting;
	GridBagConstraints g;
	User user;
	
	GUIListenerSupport gls;
	
	private Appointment app;
	private Meeting meet;
	
	/**
	 * Constructor for NewPanel
	 */
	public NewPanel(){
		gls = new GUIListenerSupport();
		setLayout(new GridBagLayout());
		g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.CENTER;
		
		isMeetingPanel=false;
		isMeeting=false;
		newMeetingPanel = new NewMeetingPanel();
		newMeetingPanel.addGuiListener(this);
		newAppointmentPanel = new NewAppointmentPanel();
		newAppointmentPanel.addGuiListener(this);
		redraw();
	}
	
	/**
	 * redraws the panel to whatever is needed
	 */
	public void redraw(){
		removeAll();
		if(!isMeetingPanel){
			add(newAppointmentPanel,g);
		}else{
			add(newMeetingPanel,g);
		}
		repaint();
		revalidate();
	}
	
	/**
	 * Listenes for changes in the other panels so redraw can change to the right panel
	 * @see redraw()
	 */
	@Override
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		if(ct==ChangeType.MEETING){
			isMeetingPanel=true;
			isMeeting=true;
		}else if(ct==ChangeType.BACK){
			isMeetingPanel=false;
		}else if(ct==ChangeType.CANCEL){
			System.exit(0);
		}else if(ct==ChangeType.CREATEMEETING){
			saveData();
			gls.notifyListeners(ct, null);
		}else if(ct==ChangeType.CREATE){
			saveData();
			System.exit(0);
		}else{
			isMeetingPanel=false;
		}
		redraw();
	}
	
	public void addGuiListener(GUIListener listener){
		gls.add(listener);
	}
	
	private ArrayList<User> toMakeThisFrickingWork(User[] participants){
		List newList = new ArrayList();
		Collections.addAll(newList, participants);
		return (ArrayList<User>) newList;
	}
	
	/**
	 * Method to save data that has been entered. 
	 */
	private void saveData(){
		Timestamp startTimestamp = new Timestamp(0);
		Timestamp endTimestamp = new Timestamp(0);
		
		startTimestamp.setTime(getMillitime(newAppointmentPanel.getStartYear(), getMonthNumber(newAppointmentPanel.getStartMonth()), 
				newAppointmentPanel.getStartDay(), newAppointmentPanel.getStartTime()[0], newAppointmentPanel.getStartTime()[1],0));
		
		endTimestamp.setTime(getMillitime(newAppointmentPanel.getEndYear(),getMonthNumber(newAppointmentPanel.getEndMonth()),
				newAppointmentPanel.getEndDay(), newAppointmentPanel.getEndTime()[0], newAppointmentPanel.getEndTime()[1],0));
		
		int roomNumber = newMeetingPanel.getRoomNumber();
		
		if(!isMeeting){
			System.out.println("Lager avtale");

			app = DBAppointment.newAppointment(startTimestamp.getTime(), endTimestamp.getTime(), newAppointmentPanel.getWhat(), newAppointmentPanel.getDesc());
			System.out.println("ID: "+app.getId());
		}
		else if(isMeeting){
			meet = DBMeeting.newMeeting(user, roomNumber, startTimestamp.getTime(), endTimestamp.getTime(), newAppointmentPanel.getDesc(), newAppointmentPanel.getWhat(), toMakeThisFrickingWork(newMeetingPanel.getParticipants()));
			for(int i=0;i<newMeetingPanel.getParticipants().length;i++) 
				meet.addParticipant(newMeetingPanel.getParticipants()[i]);

		}
	}
	
	/**
	 * This method takes in a date and converts it into milliseconds. 
	 * @param year
	 * 			Takes a year argument as an <code>int</code>
	 * @param month
	 * 			Takes a month argument as an <code>int</code>
	 * @param day
	 * 			Takes a day argument as an <code>int</code>
	 * @param hour
	 * 			Takes an hour argument as an <code>int</code>
	 * @param min
	 * 			Takes a min argument as an <code>int</code>
	 * @param sec
	 * 			Takes a sec argument as an <code>int</code>
	 * @return Returns the computed value as a <code>long</code> in milliseconds
	 */
	public static long getMillitime(int year, int month, int day, int hour, int min, int sec){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, min, sec);
		return cal.getTimeInMillis();
	}
	
	/**
	 * Takes a month as a <code>string</code> in norwegian and converts it into a 0 based month number
	 * Also used by EditPanel
	 * 
	 * @param month
	 * 			Monthname as a <code>string</code> in Norwegian
	 * @return returns the 0 based number of the month
	 * @see EditPanel
	 */
	public static int getMonthNumber(String month){
		String[] monthStr = { "Januar", "Februar", "Mars", "April", "Mai", "Juni","Juli", "August", "September", "Oktober", "November","Desember" };
		for (int i=0;i<monthStr.length;i++)
			if(monthStr[i].equals(month))
				return i;
		return -1;
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Appointment");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new NewPanel());
		frame.pack();
		frame.setVisible(true);
	}
	
	public void setUser(User u){
		this.user = u;
	}
	
}
