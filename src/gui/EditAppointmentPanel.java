package gui;

/**
 * -*- coding:utf-8 -*-
 */

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Appointment;
import model.DBAppointment;
import util.ChangeType;
import util.GUIListener;
import util.GUIListenerSupport;

public class EditAppointmentPanel extends AppointmentPanel {
	
	JButton delete;
	
	public EditAppointmentPanel() {
		delete = new JButton("Slett");
		super.btng.gridx=3;
		super.btnPanel.add(delete,super.btng);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new EditAppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
