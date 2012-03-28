package gui;

/**
 * -*- coding:utf-8 -*-
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import util.ChangeType;

public class EditAppointmentPanel extends AppointmentPanel implements ActionListener {
	
	JButton delete;
	
	public EditAppointmentPanel() {
		super();
		
		invite.setText("Edit meeting");
		
		delete = new JButton("Slett");
		delete.addActionListener(this);
		
		btng.gridx=3;
		btnPanel.add(delete,btng);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==delete){
			gls.notifyListeners(ChangeType.DELETE, null);
		}
	}
}
