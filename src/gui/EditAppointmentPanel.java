package gui;

/**
 * -*- coding:utf-8 -*-
 */

import javax.swing.JButton;
import javax.swing.JFrame;

public class EditAppointmentPanel extends AppointmentPanel {
	
	JButton delete;
	
	public EditAppointmentPanel() {
		super();
		
		invite.setText("Edit meeting");
		
		delete = new JButton("Slett");
		
		btng.gridx=3;
		btnPanel.add(delete,btng);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new EditAppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
