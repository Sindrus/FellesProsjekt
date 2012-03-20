package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author sindre
 * 
 * Panelet som inneholder den ukentlige kalendern.
 * 
 */

public class WeeklyCalendarPanel extends JPanel {
	
	static Calendar c = Calendar.getInstance();
	static String[] weekdays = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lordag", "Sondag"};
	static String[] monthnames = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "Semptember", "Oktober", "November", "Desember"};
	static int weeknum = c.get(Calendar.WEEK_OF_YEAR);
	static int daynum = c.get(Calendar.DAY_OF_MONTH);
	static int monthnum = Calendar.MONTH+1;
	
	public WeeklyCalendarPanel(){
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		JLabel ukenummer = new JLabel("Ukenummer: " + Integer.toString(weeknum));
		add (ukenummer,g);
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new WeeklyCalendarPanel());
		frame.pack();
		frame.setVisible(true);
		System.out.println(weeknum);  // Proper use. Week number.
		System.out.println(daynum); // Day number.
	 	System.out.println(monthnum); // Month number.
		System.out.println(weekdays[c.get(Calendar.DAY_OF_WEEK)-2]); //Proper use. Day of the week (Name).
		System.out.println(monthnames[c.get(Calendar.MONTH)]); //Proper use. Month of the year (Name).
	}
}