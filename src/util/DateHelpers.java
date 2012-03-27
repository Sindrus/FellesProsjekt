package util;

/*
 * DateHelpers
 * 
 * Author: Even
 * Version: 1.0
 * 
 * Class containing methods for easier interaction between the model and the 
 * database layers
 */

import java.util.HashMap;

/**
 * Class containing methods to ease the interaction between model and database
 * 
 * @author Even
 * @version 1.0
 */

public class DateHelpers {

	/**
	 * Converts separated time unit inputs into an sql <code>Timestamp</code>
	 * @param year
	 * @param month
	 * @param dayNum
	 * @param hour
	 * @param minute
	 * @param second
	 * @return a <code>long</code> representing the given date, year and time 
	 * 			of day as a time stamp
	 */
	public static long convertToTimestamp(int year, int month, int day,
			int hour, int minute, int second){
		
		return Long.parseLong(format(year) + format(month) + 
				format(day)+ format(hour) + format(minute) + 
				format(second));
		
	}
	
	/**
	 * Converts time unit inputs into an sql <code>Timestamp</code>
	 * @param year
	 * @param month
	 * @param dayNum
	 * @param hour
	 * @param minute
	 * @return a <code>long</code> representing the given date, year and time
	 * 			of day as a time stamp
	 */
	public static long convertToTimestamp(int year, int month, int day,
			int hour, int minute){
		return convertToTimestamp(year, month, day, hour, minute, 0);
	}
	
	
	/**
	 * Converts time unit inputs into an sql <code>Timestamp</code>
	 * @param year
	 * @param month
	 * @param dayNum
	 * @param hour
	 * @return a <code>long</code> representing the given date, year and time
	 * 			of day as a time stamp
	 */
	public static long convertToTimestamp(int year, int month, int day, 
			int hour){
		return convertToTimestamp(year, month, day, hour, 0);
	}
	
	/**
	 * Puts values from a <code>long</code> timestamp into a 
	 * <code>HashMap</code> with <code>String</code>s as keys 
	 * @param timestamp
	 * 			The <code>long</code> given as time stamp
	 * @return A <code>HashMap</code> containing <code>String</code> keys and
	 * 			<code>Integer</code> values
	 */
	public static HashMap<String, Integer> convertFromTimestamp(long timestamp){
		
		int year, month, day, hour, minute, second;
		second = (int)(timestamp % 100);
		timestamp /= 100;
		minute = (int)(timestamp % 100);
		timestamp /= 100;
		hour = (int)(timestamp % 100);
		timestamp /= 100;
		day = (int)(timestamp % 100);
		timestamp /= 100;
		month = (int)(timestamp % 100);
		timestamp /= 100;
		year = (int)timestamp;
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("year", year);
		map.put("month", month);
		map.put("day", day);
		map.put("hour", hour);
		map.put("minute", minute);
		map.put("second", second);
		
		return map;
		
	}
	
	/**
	 * Formats an int to have leading zeros if it should
	 * @param input
	 * @return a formatted String
	 */
	private static String format(int input){
		
		return (input < 10 ? "0"+input : ""+input);
		
	}
	
}
