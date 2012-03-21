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
 */

public class NewPanel extends JPanel implements GUIListener{
	
	NewMeetingPanel newMeetingPanel;
	NewAppointmentPanel newAppointmentPanel;
	private boolean isMeeting;
	GridBagConstraints g;
	
	public NewPanel(){
		setLayout(new GridBagLayout());
		g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.CENTER;
		
		isMeeting=false;
		newMeetingPanel = new NewMeetingPanel();
		newMeetingPanel.addGuiListener(this);
		newAppointmentPanel = new NewAppointmentPanel();
		newAppointmentPanel.addGuiListener(this);
		redraw();
	}
	
	public void redraw(){
		removeAll();
		if(!isMeeting){
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
			isMeeting=true;
		}else if(ct==ChangeType.BACK){
			isMeeting=false;
		}else if(ct==ChangeType.CANCEL){
			System.exit(0);
		}else{
			isMeeting=false;
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
