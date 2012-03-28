package gui;

/**
 * -*- coding:utf-8 -*-
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import util.ChangeType;
import util.DateHelpers;

public class EditAppointmentPanel extends AppointmentPanel implements ActionListener {
	
	JButton delete;
	int appointmentID;
	
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
		}else if(e.getSource()==cancel){
			ArrayList<Object> array = new ArrayList<Object>();
			gls.notifyListeners(ChangeType.CANCEL, array);			
		}else if(e.getSource()==invite){
			ArrayList<Object> array = new ArrayList<Object>();
			gls.notifyListeners(ChangeType.MEETING, array);
		}else if(e.getSource()==complete){
			ArrayList<Object> array = new ArrayList<Object>();
			
			model.DBAppointment.changeAppointmentDescription(appointmentID, description.getText());
			
			long startTimestamp = 0L;
			long endTimestamp = 0L;
			
			startTimestamp = DateHelpers.convertToTimestamp(getStartYear(), NewPanel.getMonthNumber(getStartMonth()), 
					getStartDay(), getStartTime()[0], getStartTime()[1]);
			
			endTimestamp = DateHelpers.convertToTimestamp(getEndYear(),NewPanel.getMonthNumber(getEndMonth()),
					getEndDay(), getEndTime()[0], getEndTime()[1]);
			
			
			model.DBAppointment.changeTimeOfAppointment(startTimestamp, endTimestamp, appointmentID);
			gls.notifyListeners(ChangeType.CREATE, array);
		}
	}
}
