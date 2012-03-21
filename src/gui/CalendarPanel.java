package gui;

import javax.swing.JLabel;
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

public class CalendarPanel extends JPanel {
	
	
	
	public CalendarPanel(){
		add(new CalendarListPanel());
	}
}
