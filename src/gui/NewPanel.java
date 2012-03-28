package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Appointment;
import model.DBAppointment;
import model.DBMeeting;
import model.DBRoom;
import model.DBUser;
import model.Meeting;
import model.NoAvailableRoomsException;
import model.User;
import util.ChangeType;
import util.DateHelpers;
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
	public void redraw() {
		removeAll();
		
		if(!isMeetingPanel){
			System.out.println("redrawing new panel_______________________________________");
			add(newAppointmentPanel,g);
		}else{
			long startTimestamp = 0L;
			long endTimestamp = 0L;

			startTimestamp = DateHelpers.convertToTimestamp(newAppointmentPanel.getStartYear(), getMonthNumber(newAppointmentPanel.getStartMonth()), 
					newAppointmentPanel.getStartDay(), newAppointmentPanel.getStartTime()[0], newAppointmentPanel.getStartTime()[1],0);

			endTimestamp = DateHelpers.convertToTimestamp(newAppointmentPanel.getEndYear(),getMonthNumber(newAppointmentPanel.getEndMonth()),
					newAppointmentPanel.getEndDay(), newAppointmentPanel.getEndTime()[0], newAppointmentPanel.getEndTime()[1]);
			try {
				newMeetingPanel.fillLists(DBRoom.getAvailibleRooms(1, startTimestamp, endTimestamp), DBUser.getUsersInSystem());
			} catch (NoAvailableRoomsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			System.out.println("ct == meeting");
			isMeetingPanel=true;
			isMeeting=true;
			redraw();
		}else if(ct==ChangeType.BACK){
			isMeetingPanel=false;
			redraw();
		}else if(ct==ChangeType.CANCEL){
			gls.notifyListeners(ChangeType.CALENDAR, null);
		}else if(ct==ChangeType.CREATEMEETING){
			notifyGui(ChangeType.CREATE, null);
		}else if(ct==ChangeType.CREATE){
			saveData();
			System.out.println("returning to calendar ___________________________________");
			gls.notifyListeners(ChangeType.CALENDAR, null);
		}else{
			isMeetingPanel=false;
			redraw();
		}
		
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
		long startTimestamp = 0L;
		long endTimestamp = 0L;
		startTimestamp = DateHelpers.convertToTimestamp(newAppointmentPanel.getStartYear(), getMonthNumber(newAppointmentPanel.getStartMonth()), 
				newAppointmentPanel.getStartDay(), newAppointmentPanel.getStartTime()[0], newAppointmentPanel.getStartTime()[1],0);

		endTimestamp = DateHelpers.convertToTimestamp(newAppointmentPanel.getEndYear(),getMonthNumber(newAppointmentPanel.getEndMonth()),
				newAppointmentPanel.getEndDay(), newAppointmentPanel.getEndTime()[0], newAppointmentPanel.getEndTime()[1]);

		int roomNumber = newMeetingPanel.getRoomNumber();

		if(!isMeeting){
			System.out.println("Lager avtale");

			System.out.println(startTimestamp);
			app = DBAppointment.newAppointment(user.getId(), startTimestamp, endTimestamp, newAppointmentPanel.getWhat(), newAppointmentPanel.getDesc());
			System.out.println("ID: "+app.getId());
		}
		else if(isMeeting){
			System.out.println("creating meeting in DB");
			meet = DBMeeting.newMeeting(user, roomNumber, startTimestamp, endTimestamp, newAppointmentPanel.getWhat(),newAppointmentPanel.getDesc(),  toMakeThisFrickingWork(newMeetingPanel.getParticipants()));
			System.out.println("Meeting ID: " + meet.getId());


		}
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

	//	public static void main(String args[]) {
	//		JFrame frame = new JFrame("Appointment");
	//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//		frame.add(new NewPanel());
	//		frame.pack();
	//		frame.setVisible(true);
	//	}

	public void setUser(User u){
		this.user = u;
	}

}
