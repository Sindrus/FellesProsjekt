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
	
	int weeknum = Calendar.getInstance().WEEK_OF_MONTH;

	public static void main(String args[]) {
		 System.out.println(Calendar.getInstance().getTime()); //<----Displays Time (Day(week),month,day(number),time,timezone,year.
		}
	}
