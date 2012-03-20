package database;

/*
 * Authentication
 * 
 * Author: Even
 * Version: 1.0
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for authentication of username and password
 * @author Even
 * @version 1.0
 */

public class Authentication {
	
	/**
	 * Checks that the submitted username and password are valid
	 * @param user
	 * 			The submitted username
	 * @param pass
	 * 			The submitted password
	 * @return	<code>1</code> if username and password are both valid;
	 * 			<code>0</code> if password is incorrect;
	 * 			<code>-1</code> if username does not exist in database
	 */
	
	public static int inputIsValid(String user, String pass){
		
		String actualPass = getActualPassword(user);
		if(actualPass == null){
			return -1;
		} else if(pass.equals(actualPass)){
			return 1;
		} else{
			return 0;
		}
		
	}
	
	/**
	 * Fetches the corresponding password for the submitted user if the user
	 * exists
	 * @param user
	 * 			The submitted username
	 * @return a <code>String</code> containing the corresponding password
	 * 			if the username is valid; otherwise <code>null</code>
	 */
	
	private static String getActualPassword(String user){
		
		String sql = "SELECT Brukernavn, Passord FROM Bruker WHERE " +
				"Brukernavn = '" + user + "';";
		
		String pass = "";
		try {
			ResultSet rs = Database.execute(sql);
			if(rs.next()){
				pass = rs.getString("Passord");
			} else{
				pass = null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			pass = null;
		}
		return pass;

	}
	
}