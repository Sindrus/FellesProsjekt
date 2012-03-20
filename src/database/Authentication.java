package database;

<<<<<<< HEAD
/**
 * Når en bruker logger på vil det lages et nytt Authentication object, som inneholder 
 * informasjon om brukeren:
 * 	brukernavn
 * 	navn
 * 	 
 * Skal også ha en metode som sier hvorvidt brukeren er autorisert.
 * 
=======
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
>>>>>>> 4793ee36ee2b879c56161a6c73792961de14d04f
 */

public class Authentication {
	

	private boolean isAuthenicated;
	
//	Eksempellogin frem til vi får satt opp database	
	final static String user ="Per";
	final static String pass ="hemmelig";
	
	public Authentication(String username, String password){
	
		isAuthenicated = false;
	}
	
	public void authenticate(String username, String password){
		
// Midlertidig brukergodkjenning, frem til vi får satt opp tilkobling til database
		if(username.equals(user)==true && password.equals(pass)==true)
			isAuthenicated=true;
		else
			isAuthenicated=false;
	}

//	Returnerer om en bruker er logget inn eller ikke.	
	public boolean getAuthenicationStatus(){
		return isAuthenicated;

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