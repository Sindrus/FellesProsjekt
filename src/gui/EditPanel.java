
package gui;

import java.awt.GridBagConstraints;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.GUIController;

import model.Appointment;
import model.Meeting;
import model.User;
import util.ChangeType;
import util.DateHelpers;
import util.GUIListener;
import util.GUIListenerSupport;

/**
 * 
 * @author sindre
 * 
 * Panelet som tillater å endre ting
 * 
 * TODO: Need a contstructor that takes in whatever information that needs to be edited
 * 
 */

public class EditPanel extends JPanel implements GUIListener{
	
	EditMeetingPanel editMeetingPanel;
	EditAppointmentPanel editAppointmentPanel;
	private boolean isMeetingPanel;
	private boolean isMeeting;
	GridBagConstraints g;
	private Appointment app;
	private Meeting meet;
	private GUIListenerSupport gls;
	
	/**
	 * Constructs an <code>EditPanel</code> based on an appointment
	 * If this calendarprogram is ever implementing a function to allow the user to select minutes,
	 * this method needs to be changed!
	 * @param app
	 * 			Takes <code>Appointment</code> as a parameter so it can populate all fields
	 */
	public EditPanel(Appointment app){
		
		this();
		this.app = app;
		isMeeting = false;
gls = new GUIListenerSupport();
		editAppointmentPanel.what.setText(app.getTitle());

		HashMap<String, Integer> startValues = DateHelpers.convertFromTimestamp(app.getStart());
		HashMap<String, Integer> endValues = DateHelpers.convertFromTimestamp(app.getEnd());
		System.out.println("_______________________");
		System.out.println(app.getId());
		System.out.println(app.getStart());
		System.out.println(app.getEnd());
		editAppointmentPanel.startDay.setSelectedItem(startValues.get("day"));
		editAppointmentPanel.startMonth.setSelectedIndex(startValues.get("month"));
		editAppointmentPanel.startYear.setText(Integer.toString(startValues.get("year")));
		editAppointmentPanel.endDay.setSelectedItem(endValues.get("day"));
		editAppointmentPanel.endMonth.setSelectedIndex(endValues.get("month"));
		editAppointmentPanel.endYear.setText(Integer.toString(endValues.get("year")));
		editAppointmentPanel.description.setText(app.getDescription());
	}
	
	public EditPanel(Meeting app){
		this();
		this.meet = app;
		isMeeting = true;

		editAppointmentPanel.what.setText(app.getTitle());
		HashMap<String, Integer> startValues = DateHelpers.convertFromTimestamp(app.getStart());
		HashMap<String, Integer> endValues = DateHelpers.convertFromTimestamp(app.getEnd());
		
		
	// Yes, I know all the get methods are deprecated, but thats what you get for using timestamp...
		editAppointmentPanel.startDay.setSelectedItem(startValues.get("day"));
		editAppointmentPanel.startMonth.setSelectedIndex(startValues.get("month"));
		editAppointmentPanel.startYear.setText(Integer.toString(startValues.get("year")));
		editAppointmentPanel.endDay.setSelectedItem(endValues.get("day"));
		editAppointmentPanel.endMonth.setSelectedIndex(endValues.get("month"));
		editAppointmentPanel.endYear.setText(Integer.toString(endValues.get("year")));
		editAppointmentPanel.description.setText(app.getDescription());
		
		
	}
	
	public void getItemsToSelect(){
		
	}
	
	public EditPanel(){
		isMeetingPanel = false;
		isMeeting = false;
		editMeetingPanel = new EditMeetingPanel();
		editMeetingPanel.addGuiListener(this);
		editAppointmentPanel = new EditAppointmentPanel();
		editAppointmentPanel.addGuiListener(this);
		g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;
		
		redraw();
	}
	
	public void redraw(){
		removeAll();
		if(isMeetingPanel){
			add(editMeetingPanel);
		}else{
			add(editAppointmentPanel);
		}
		
		repaint();
		revalidate();
	}
	
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		if(ct==ChangeType.MEETING){
			isMeeting = true;
			isMeetingPanel=true;
		}else if(ct==ChangeType.BACK){
			isMeetingPanel=false;
		}else if(ct==ChangeType.CANCEL){
			gls.notifyListeners(ChangeType.CALENDAR, null);
		}else if(ct==ChangeType.CREATEMEETING || ct==ChangeType.CREATE){
			saveChanges();
		}else if(ct==ChangeType.DELETE){
			gls.notifyListeners(ct, null);
		}else{
			isMeetingPanel=false;
		}
		redraw();
	}
	
	public boolean getIsMeeting(){
		return isMeeting;
	}
	
	public void saveChanges(){
		long startTimestamp = 0L;
		long endTimestamp = 0L;
		
		startTimestamp = DateHelpers.convertToTimestamp(editAppointmentPanel.getStartYear(), NewPanel.getMonthNumber(editAppointmentPanel.getStartMonth()), 
				editAppointmentPanel.getStartDay(), editAppointmentPanel.getStartTime()[0], editAppointmentPanel.getStartTime()[1]);
		
		endTimestamp = DateHelpers.convertToTimestamp(editAppointmentPanel.getEndYear(),NewPanel.getMonthNumber(editAppointmentPanel.getEndMonth()),
				editAppointmentPanel.getEndDay(), editAppointmentPanel.getEndTime()[0], editAppointmentPanel.getEndTime()[1]);
		
		if(isMeeting)
			saveMeeting(startTimestamp,endTimestamp);
		else
			saveAppointment(startTimestamp,endTimestamp);
	}
	private void saveMeeting(long start, long end){
		meet.setDescription(editAppointmentPanel.getDesc());
		meet.setTitle(editAppointmentPanel.getWhat());
		meet.setStart(start);
		meet.setEnd(end);
		meet.setRoom(editMeetingPanel.getRoom());
		meet.removeParticipants();
		ArrayList<User> users= new ArrayList<User>();
		for(Object user : editMeetingPanel.getSelectedParticipants())
			users.add((User)user);
			
		meet.setParticipants(users);
		
	}
	private void saveAppointment(long start, long end){
		app.setDescription(editAppointmentPanel.getDesc());
		app.setTitle(editAppointmentPanel.getWhat());
		app.setStart(start);
		app.setEnd(end);
	}
	
	
	public void addListener(GUIListener l){
		gls.add(l);
	}
}

