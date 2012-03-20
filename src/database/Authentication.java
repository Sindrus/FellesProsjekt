package database;

/**
 * Når en bruker logger på vil det lages et nytt Authentication object, som inneholder 
 * informasjon om brukeren:
 * 	brukernavn
 * 	navn
 * 	 
 * Skal også ha en metode som sier hvorvidt brukeren er autorisert.
 * 
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
	}
	
}