package gui;

import java.awt.GridBagConstraints;
import java.sql.Timestamp;
import java.util.ArrayList;
import gui.NewPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Appointment;
import model.Meeting;
import util.ChangeType;
import util.GUIListener;

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
		
		editAppointmentPanel.what.setText(app.getWhat());
	// Yes, I know all the get methods are deprecated, but thats what you get for using timestamp...
		editAppointmentPanel.startDay.setSelectedItem(app.getStart().getDay());
		editAppointmentPanel.startMonth.setSelectedIndex(app.getStart().getMonth());
		editAppointmentPanel.startYear.setText(Integer.toString(app.getStart().getYear()));
		editAppointmentPanel.endDay.setSelectedItem(app.getEnd().getDay());
		editAppointmentPanel.endMonth.setSelectedIndex(app.getEnd().getMonth());
		editAppointmentPanel.endYear.setText(Integer.toString(app.getEnd().getYear()));
		editAppointmentPanel.description.setText(app.getDescription());
	}
	
	public EditPanel(Meeting app){
		this();
		this.meet = app;
		isMeeting = true;
		
		editAppointmentPanel.what.setText(app.getWhat());
	// Yes, I know all the get methods are deprecated, but thats what you get for using timestamp...
		editAppointmentPanel.startDay.setSelectedItem(app.getStart().getDay());
		editAppointmentPanel.startMonth.setSelectedIndex(app.getStart().getMonth());
		editAppointmentPanel.startYear.setText(Integer.toString(app.getStart().getYear()));
		editAppointmentPanel.endDay.setSelectedItem(app.getEnd().getDay());
		editAppointmentPanel.endMonth.setSelectedIndex(app.getEnd().getMonth());
		editAppointmentPanel.endYear.setText(Integer.toString(app.getEnd().getYear()));
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
			System.exit(0);
		}else if(ct==ChangeType.CREATEMEETING || ct==ChangeType.CREATE){
			saveChanges();
		}else{
			isMeetingPanel=false;
		}
		redraw();
	}
	
	public void saveChanges(){
		Timestamp startTimestamp = new Timestamp(0);
		Timestamp endTimestamp = new Timestamp(0);
		
		startTimestamp.setTime(NewPanel.getMillitime(editAppointmentPanel.getStartYear(), NewPanel.getMonthNumber(editAppointmentPanel.getStartMonth()), 
				editAppointmentPanel.getStartDay(), editAppointmentPanel.getStartTime()[0], editAppointmentPanel.getStartTime()[1],0));
		
		endTimestamp.setTime(NewPanel.getMillitime(editAppointmentPanel.getEndYear(),NewPanel.getMonthNumber(editAppointmentPanel.getEndMonth()),
				editAppointmentPanel.getEndDay(), editAppointmentPanel.getEndTime()[0], editAppointmentPanel.getEndTime()[1],0));
		
		if(isMeeting)
			saveMeeting(startTimestamp,endTimestamp);
		else
			saveAppointment(startTimestamp,endTimestamp);
	}
	private void saveMeeting(Timestamp start, Timestamp end){
		meet.setDescription(editAppointmentPanel.getDesc());
		meet.setWhat(editAppointmentPanel.getWhat());
		meet.setStart(start);
		meet.setEnd(end);
		meet.setRoom(editMeetingPanel.getRoom());
		meet.removeParticipants();
		meet.setParticipants(editMeetingPanel.getSelectedParticipants());
		
	}
	private void saveAppointment(Timestamp start, Timestamp end){
		app.setDescription(editAppointmentPanel.getDesc());
		app.setWhat(editAppointmentPanel.getWhat());
		app.setStart(start);
		app.setEnd(end);
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Appointment");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new EditPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
