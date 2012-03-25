package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author sindre
 *
 * Skal inneholde 3 paneler: Innkalinger, ukekalender og kalenderliste
 * Skal kunne lage en ny JFrame som inneholder et varselpanel
 * 
 *  CalendarListPanel
 *  WeeklyCalendarPanel
 *  InvitesPanel
 * 
 */

public class CalendarPanel extends JPanel{
	
	JButton newap, logout, vars;
	private JPanel btntopleftPanel, btntoprightPanel,listPanel,btnbtmPanel;
	
	public CalendarPanel(){
		
		GridBagConstraints g = new GridBagConstraints();
		setLayout(new GridBagLayout());
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;
		
		//New Appointment.
		btntopleftPanel = new JPanel();
		GridBagConstraints btntopleftg = new GridBagConstraints();
		btntopleftg.anchor = GridBagConstraints.NORTHWEST;
		
		btntopleftg.gridx = 0;
		btntopleftg.gridy = 0;
		newap = new JButton("Ny Avtale");
		newap.addActionListener(new newApp());
		btntopleftPanel.add(newap,btntopleftg);
		
		//end the AppointmentButton.
		
		g.gridx = 0;
		g.gridy = 0;
		add(btntopleftPanel, g);
		
		//Logout.
		btntoprightPanel = new JPanel();
		GridBagConstraints btntoprightg = new GridBagConstraints();
		btntoprightg.anchor = GridBagConstraints.NORTHEAST;
		
		btntoprightg.gridx = 0;
		btntoprightg.gridy = 0;
		logout = new JButton("Logg ut");
		logout.addActionListener(new logOut());
		btntoprightPanel.add(logout,btntopleftg);
		//finish logout.
		
		g.gridx = 2;
		g.gridy = 0;
		add(btntoprightPanel, g);
		
		
		//CalendarListPanel and InvitationListPanel
		listPanel = new JPanel(new GridBagLayout());
		GridBagConstraints lisg = new GridBagConstraints();
		lisg.insets = new Insets(5, 5, 5, 5);
		lisg.anchor = GridBagConstraints.NORTHWEST;
		
		
		lisg.gridx = 0;
		lisg.gridy = 0;
		listPanel.add(new CalendarListPanel(), lisg);
		
		lisg.gridx = 0;
		lisg.gridy = 1;
		listPanel.add(new InvitationListPanel(), lisg);
		
		//end the two lists.
		g.gridx = 0;
		g.gridy = 1;
		add(listPanel, g);
		
		//WeeklyCalendar
		g.gridx = 1;
		g.gridy = 1;
		//add(new WeeklyCalendarPanel(), g);
		//finish.
		
		//Bottom Button Panel
		btnbtmPanel = new JPanel(new GridBagLayout());
		GridBagConstraints btnbtmg = new GridBagConstraints();
		btntoprightg.anchor = GridBagConstraints.SOUTHWEST;
		
		btntoprightg.gridx = 0;
		btntoprightg.gridy = 0;
		vars = new JButton("Varslinger");
		vars.addActionListener(new varS());
		btnbtmPanel.add(vars,btnbtmg);
		
		//Varslinger.
		g.gridx = 0;
		g.gridy = 2;
		add(btnbtmPanel, g);
	}
	

	
	class newApp implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class varS implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class logOut implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Kalender");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new CalendarPanel());
		frame.pack();
		frame.setVisible(true);
	}

}
