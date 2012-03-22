package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ChangeType;
import util.GUIListener;

/**
 * 
 * @author sindre
 * 
 * Inneholder paneler for ny avtale og nytt møte
 * NewAppointmentPanel
 * NewMeetingPanel
 * 
 * To create new appointment id use:
 * 
 * DBAppointment.newAppointment(Timestamp startTimestamp, Timestamp endTimestamp, String desc)
 * returnerer et nytt appointmentobject.
 * 
 */

public class NewPanel extends JPanel implements GUIListener{
	
	NewMeetingPanel newMeetingPanel;
	NewAppointmentPanel newAppointmentPanel;
	private boolean isMeetingPanel;
	private boolean isMeeting;
	GridBagConstraints g;
	
	public NewPanel(){
		setLayout(new GridBagLayout());
		g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.CENTER;
		
		isMeetingPanel=false;
		newMeetingPanel = new NewMeetingPanel();
		newMeetingPanel.addGuiListener(this);
		newAppointmentPanel = new NewAppointmentPanel();
		newAppointmentPanel.addGuiListener(this);
		redraw();
	}
	
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

	@Override
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		if(ct==ChangeType.MEETING){
			isMeetingPanel=true;
		}else if(ct==ChangeType.BACK){
			isMeetingPanel=false;
		}else if(ct==ChangeType.CANCEL){
			System.exit(0);
		}else if(ct==ChangeType.CREATEMEETING){
			
		}else{
			isMeetingPanel=false;
		}
		redraw();
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Appointment");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new NewPanel());
		frame.pack();
		frame.setVisible(true);
	}

	
}
