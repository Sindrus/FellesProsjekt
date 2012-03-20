package gui;

import java.util.Calendar;

/**
 * 
 * @author sindre
 * 
 * Panelet som inneholder den ukentlige kalendern.
 * 
 */

public class WeeklyCalendarPanel {
	
	
	public static void main(String args[]) {
		
		Calendar c = Calendar.getInstance();
		String[] weekdays = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lordag", "Sondag"};
		String[] monthnames = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "Semptember", "Oktober", "November", "Desember"};
		int weeknum = c.get(Calendar.WEEK_OF_YEAR);
		int daynum = c.get(Calendar.DAY_OF_MONTH);
		int monthnum = Calendar.MONTH+1;
		
		System.out.println(weeknum);  // Proper use. Week number.
		System.out.println(daynum); // Day number.
	 	System.out.println(monthnum); // Month number.
		System.out.println(weekdays[c.get(Calendar.DAY_OF_WEEK)-2]); //Proper use. Day of the week (Name).
		System.out.println(monthnames[c.get(Calendar.MONTH)]); //Proper use. Month of the year (Name).
	}
}