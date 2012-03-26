package gui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * 
 * @author sindre
 * 
 * Panel som inneholder en liste over alle møteinvitasjoner
 * 
 */

public class NotificationPanel extends JPanel{
	JList list = new JList();
	
	NotificationPanel(){
		list = new JList();
		add(list);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Appointment");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new NotificationPanel());
		frame.pack();
		frame.setVisible(true);
	}
}