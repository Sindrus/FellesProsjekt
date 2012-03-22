package util;

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
	public static long convertToTimestamp(int year, int month, int dayNum,
			int hour, int minute, int second){
		
		return Long.parseLong(format(year) + format(month) + 
				format(dayNum)+ format(hour) + format(minute) + 
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
	public static long convertToTimestamp(int year, int month, int dayNum,
			int hour, int minute){
		return convertToTimestamp(year, month, dayNum, hour, minute, 0);
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
	public static long convertToTimestamp(int year, int month, int dayNum, 
			int hour){
		return convertToTimestamp(year, month, dayNum, hour, 0, 0);
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
