package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.ArrayList;
import javax.swing.JPanel;
import model.User;
import util.ChangeType;
import util.GUIListener;

/**
 * 
 * Dette vil være selve 'driveren' til guiet.
 * Kan skal kunne vise flere forskjellige paneler:
 * 
 * LoginPanel
 * CalendarPanel
 * NewPanel
 * DeletePanel
 * EditPanel
 *
 */



public class ProjectPanel extends JPanel {

	
	
	LoginPanel loginPanel;
	public CalendarPanel calendarPanel;

	NewPanel newPanel;
	WeeklyCalendarPanel wcp;

	private User user;

	private Toolkit tool = Toolkit.getDefaultToolkit();

	
	public ProjectPanel()
	{
//		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
}
