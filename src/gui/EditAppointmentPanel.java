package gui;

/**
 * 
 * @author Made
 * 
 * Panelet som lar brukeren endre en avtale
 * 
 */

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditAppointmentPanel extends JPanel {
	
	JButton cancel, saveChanges, invite;
	JTextField what,description;
	JComboBox day, month, timeFrom, timeUntil;
	PropertyChangeSupport pcs;
	JLabel whenLabel,whatLabel,dayLabel,monthLabel,fromTimeLabel,untilTimeLabel,descriptionLabel;

	public EditAppointmentPanel(){
/**
 * Time in combobox
 */
		String[] time = new String[24];
		for (int i = 0; i<=23; i++)
		{
		if (i<10)
			time[i] = "0" + i + ":00";
		else
			time[i] = i + ":00";
		}
		
/**
 * Mounths and days in combobox
 */
		String[] mounths = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
		
		String[] dag = new String[32];
		for (int i = 0; i<=31; i++)
		{
		dag[i] = Integer.toString(i);
		}
		

		
		pcs = new PropertyChangeSupport(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
/*
 * add JLabels and JComboBoxes		
 */
		g.anchor = GridBagConstraints.WEST;
		g.gridy=0;
		whatLabel = new JLabel("Hva:");
		add(whatLabel,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy = 1;
		whenLabel = new JLabel("N�r:");
		add(whenLabel,g);
		
		dayLabel = new JLabel("Dag:");
		add(dayLabel,g);
		day = new JComboBox(dag);
		add(day,g);
		
		monthLabel = new JLabel("M�ned:");
		add(monthLabel,g);
		month = new JComboBox(mounths);
		add(month,g);
		
		fromTimeLabel = new JLabel("Fra:");
		add(fromTimeLabel,g);
		timeFrom = new JComboBox(time);
		add(timeFrom,g);
		
		untilTimeLabel = new JLabel("Til:");
		add(untilTimeLabel,g);
		timeUntil = new JComboBox(time);
		add(timeUntil,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=2;
		descriptionLabel = new JLabel("Beskrivelse:");
		add(descriptionLabel,g);
/*
 * add JTextFields and JButtons	
 */
		what = new JTextField(""); 
		Font font = new Font(what.getFont().getName(),what.getFont().getStyle(),14);
		what.setColumns(20);
		what.setFont(font);
		add(what,g);

		description = new JTextField("");
		g.gridwidth= 2;
		description.setFont(font);
		description.setColumns(50);
		add(description,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=3;
		cancel = new JButton("Avbryt");
		cancel.addActionListener(new Accept());
		add(cancel,g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridy=3;
		saveChanges = new JButton("Fullf�rt");
		saveChanges.addActionListener(new Deny());
		add(saveChanges,g);
		
		g.anchor = GridBagConstraints.EAST;
		g.gridy=3;
		invite = new JButton("Inviter til m�te");
		invite.addActionListener(new Deny());
		add(invite,g);
	}
	
	
	class Accept implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class Deny implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new NewAppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
}

