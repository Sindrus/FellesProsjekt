package gui;

/**
 * -*- coding:utf-8 -*-
 * 
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.ChangeType;
import util.GUIListener;
import util.GUIListenerSupport;

public class NewAppointmentPanel extends JPanel {

	JButton cancel, complete, invite;
	JPanel whatPanel, whenPanel, desPanel, btnPanel;
	JTextField what;
	JTextArea description;
	JComboBox startDay, startMonth, startTime, endDay, endMonth, endTime;
	JTextField startYear, endYear;
	GUIListenerSupport gls;
	GridBagConstraints wheng;

	/**
	 * Constructor for the <code>NewAppointmentPanel</code> that creates all the JObjects
	 */
	
	public NewAppointmentPanel() {
		String[] td = new String[24];
		for (int i = 0; i <= 23; i++) {
			if (i < 10)
				td[i] = "0" + i + ":00";
			else
				td[i] = i + ":00";
		}

		String[] mnd = { "Januar", "Februar", "Mars", "April", "Mai", "Juni",
				"Juli", "August", "September", "Oktober", "November",
				"Desember" };

		String[] dag = new String[31];
		for (int i = 1; i <= 31; i++) {
			dag[i-1] = Integer.toString(i);
		}

		gls = new GUIListenerSupport();
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.NORTHWEST;
		g.insets = new Insets(5, 5, 5, 5);

// whatPanel
		whatPanel = new JPanel();
		GridBagConstraints whatg = new GridBagConstraints();
		whatg.anchor = GridBagConstraints.WEST;

		whatg.gridy = 0;
		whatg.gridx = 0;
		JLabel h = new JLabel("Hva:");
		whatPanel.add(h, whatg);
		what = new JTextField("");
		Font f = new Font(what.getFont().getName(), what.getFont().getStyle(),
				14);
		what.setColumns(20);
		what.setFont(f);
		whatPanel.add(what, whatg);

// end whatPanel
		g.gridx = 0;
		g.gridy = 0;
		add(whatPanel, g);

// whenPanel
		whenPanel = new JPanel();
		whenPanel.setLayout(new GridBagLayout());
		wheng = new GridBagConstraints();

		wheng.anchor = GridBagConstraints.WEST;
		wheng.gridx = 0;
		wheng.gridy = 0;
		
		whenPanel.add(new JLabel("Når:"), wheng);
	// Fra tidspunkt
		wheng.insets = new Insets(5, 5, 5, 5);
		wheng.gridy=1;
		whenPanel.add(new JLabel("Fra:"),wheng);
		
		wheng.gridx = 1;
		whenPanel.add(new JLabel("Dag:"), wheng);

		wheng.gridx = 2;
		startDay = new JComboBox(dag);
		whenPanel.add(startDay, wheng);

		wheng.gridx = 3;
		whenPanel.add(new JLabel("Måned:"), wheng);

		wheng.gridx = 4;
		startMonth = new JComboBox(mnd);
		startMonth.addActionListener(new startMonth());
		whenPanel.add(startMonth, wheng);

		wheng.gridx = 5;
		whenPanel.add(new JLabel("Klokken:"), wheng);
		
		wheng.gridx = 6;
		startTime = new JComboBox(td);
		whenPanel.add(startTime, wheng);
		
		wheng.gridx = 7;
		whenPanel.add(new JLabel("År:"),wheng);
		
		wheng.gridx = 8;
		startYear = new JTextField("",4);
		whenPanel.add(startYear,wheng);
	// Til tidspunkt
		
		wheng.gridy = 2;
		wheng.gridx = 0;
		
		whenPanel.add(new JLabel("Til:"),wheng);
		
		wheng.gridx=1;
		whenPanel.add(new JLabel("Dag:"),wheng);
		
		wheng.gridx=2;
		endDay = new JComboBox(dag);
		whenPanel.add(endDay,wheng);
		
		wheng.gridx=3;
		whenPanel.add(new JLabel("Måned"),wheng);
		
		wheng.gridx=4;
		endMonth = new JComboBox(mnd);
		endMonth.addActionListener(new endMonth());
		whenPanel.add(endMonth,wheng);
		
		wheng.gridx=5;
		whenPanel.add(new JLabel("Klokken:"),wheng);
		
		wheng.gridx=6;
		endTime = new JComboBox(td);
		whenPanel.add(endTime, wheng);
		
		wheng.gridx=7;
		whenPanel.add(new JLabel("År:"),wheng);
		
		wheng.gridx=8;
		endYear = new JTextField("",4);
		whenPanel.add(endYear,wheng);

// end whenPanel
		g.gridy = 1;
		add(whenPanel, g);

// desPanel
		desPanel = new JPanel();
		desPanel.setLayout(new GridBagLayout());
		GridBagConstraints desg = new GridBagConstraints();
		desg.anchor = GridBagConstraints.NORTHWEST;
		desg.gridx = 0;
		desg.gridy = 0;

		desPanel.add(new JLabel("Beskrivelse:"), desg);

		description = new JTextArea(4, 30);
		description.setFont(f);
		desg.gridx = 1;
		desPanel.add(description, desg);

// end desPanel
		g.gridy = 2;
		add(desPanel, g);

// btnPanel
		btnPanel = new JPanel();
		btnPanel.setLayout(new GridBagLayout());
		GridBagConstraints btng = new GridBagConstraints();
		btng.anchor = GridBagConstraints.CENTER;
		btng.gridx = 0;
		btng.gridy = 0;
		btng.insets = new Insets(0, 30, 0, 30);

		complete = new JButton("Fullført");
		complete.addActionListener(new Done());
		btnPanel.add(complete, btng);

		btng.insets.left = 5;
		btng.gridx = 1;
		cancel = new JButton("Avbryt");
		cancel.addActionListener(new Cancel());
		btnPanel.add(cancel, btng);

		btng.gridx = 2;
		btng.insets.right = 0;
		invite = new JButton("Inviter til møte");
		invite.addActionListener(new Meeting());
		btnPanel.add(invite, btng);
		
// end btnPanel
		g.anchor = GridBagConstraints.CENTER;
		g.gridy = 3;
		add(btnPanel, g);
	}
	
	/**
	 * Returns the name of the event
	 * @return the name of the event as a string
	 */
	public String getWhat(){
		return what.getText();
	}
	/**
	 * Returns what day of the month the event starts
	 * @return a day of the month as a string
	 */
	public String getStartDay(){
		return startDay.getSelectedItem().toString();
	}
	/**
	 * Returns what month the event starts 
	 * @return the name of the month as a string
	 */
	public String getStartMonth(){
		return startMonth.getSelectedItem().toString();
	}
	/**
	 * Returns what time the event starts
	 * @return the time the events starts as a string
	 */
	public String getStartTime(){
		return startTime.getSelectedItem().toString();
	}
	/**
	 * Returns what year the evnent starts
	 * @return what year as a string
	 */
	public String getStartYear(){
		return startYear.getText();
	}
	/**
	 * Returns what day the event ends
	 * @return returns the day as a string
	 */
	public String getEndDay(){
		return endDay.getSelectedItem().toString();
	}
	public String getEndMonth(){
		return endMonth.getSelectedItem().toString();
	}
	public String getEndTime(){
		return endTime.getSelectedItem().toString();
	}
	public String getEndYear(){
		return endYear.getText();
	}
	public String getDesc(){
		return description.getText();
	}
	
	class Cancel implements ActionListener { // no logic.
		public void actionPerformed(ActionEvent e) {
			ArrayList<Object> array = new ArrayList<Object>();
			gls.notifyListeners(ChangeType.CANCEL, array);
		}
	}
	class Meeting implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ArrayList<Object> array = new ArrayList<Object>();
			gls.notifyListeners(ChangeType.MEETING, array);
		}
	}
	class Done implements ActionListener { // no logic.
		public void actionPerformed(ActionEvent e) {
			ArrayList<Object> array = new ArrayList<Object>();
			gls.notifyListeners(ChangeType.CREATE, array);
		}
	}
	class startMonth implements ActionListener { //Combobox listener.
		public void actionPerformed(ActionEvent e) {
			if (startMonth.getSelectedItem() == "April" || startMonth.getSelectedItem() == "Juni" || startMonth.getSelectedItem() == "September" || startMonth.getSelectedItem() == "November"){
				String[] dag = new String[30]; //days in a month.
				for (int i = 1; i <= 30; i++) {
					dag[i-1] = Integer.toString(i);
				}
				whenPanel.remove(startDay);
				validate();
				startDay = new JComboBox(dag);
				wheng.gridx = 2;
				wheng.gridy = 1;
				whenPanel.add(startDay,wheng);
				validate();
			}
			if (startMonth.getSelectedItem() == "Februar"){
				String[] dag = new String[28]; //days in a month.
				for (int i = 1; i <= 28; i++) {
					dag[i-1] = Integer.toString(i);
				}
				whenPanel.remove(startDay);
				validate();
				startDay = new JComboBox(dag);
				wheng.gridx = 2;
				wheng.gridy = 1;
				whenPanel.add(startDay,wheng);
				validate();
			}
			if (startMonth.getSelectedItem() == "Januar" || startMonth.getSelectedItem() == "Mars" || startMonth.getSelectedItem() == "Mai" || startMonth.getSelectedItem() == "Juli" || startMonth.getSelectedItem() == "August" || startMonth.getSelectedItem() == "Oktober" || startMonth.getSelectedItem() == "Desember"){
				String[] dag = new String[31]; //days in a month.
				for (int i = 1; i <= 31; i++) {
					dag[i-1] = Integer.toString(i);
				}
				whenPanel.remove(startDay);
				validate();
				startDay = new JComboBox(dag);
				wheng.gridx = 2;
				wheng.gridy = 1;
				whenPanel.add(startDay,wheng);
				validate();
			}
		}
	}
	class endMonth implements ActionListener { //Combobox listener.
			public void actionPerformed(ActionEvent e) {
				if (endMonth.getSelectedItem() == "April" || startMonth.getSelectedItem() == "Juni" || startMonth.getSelectedItem() == "September" || startMonth.getSelectedItem() == "November"){
					String[] dag = new String[30]; //days in a month.
					for (int i = 1; i <= 30; i++) {
						dag[i-1] = Integer.toString(i);
					}
					whenPanel.remove(endDay);
					validate();
					endDay = new JComboBox(dag);
					wheng.gridx = 2;
					wheng.gridy = 2;
					whenPanel.add(endDay,wheng);
					validate();
				}
				if (endMonth.getSelectedItem() == "Februar"){
					String[] dag = new String[28]; //days in a month.
					for (int i = 1; i <= 28; i++) {
						dag[i-1] = Integer.toString(i);
					}
					whenPanel.remove(endDay);
					validate();
					endDay = new JComboBox(dag);
					wheng.gridx = 2;
					wheng.gridy = 2;
					whenPanel.add(endDay,wheng);
					validate();
				}
				if (endMonth.getSelectedItem() == "Januar" || startMonth.getSelectedItem() == "Mars" || startMonth.getSelectedItem() == "Mai" || startMonth.getSelectedItem() == "Juli" || startMonth.getSelectedItem() == "August" || startMonth.getSelectedItem() == "Oktober" || startMonth.getSelectedItem() == "Desember"){
					String[] dag = new String[31]; //days in a month.
					for (int i = 1; i <= 31; i++) {
						dag[i-1] = Integer.toString(i);
					}
					whenPanel.remove(endDay);
					validate();
					endDay = new JComboBox(dag);
					wheng.gridx = 2;
					wheng.gridy = 2;
					whenPanel.add(endDay,wheng);
					validate();
				}
		}
	}
	
	public void addGuiListener(GUIListener listener){
		gls.add(listener);
	}

//	public static void main(String args[]) {
//		JFrame frame = new JFrame();
//		frame.add(new NewAppointmentPanel());
//		frame.pack();
//		frame.setVisible(true);
//	}
}
