package database;

/**
 * 
 * @deprecated Moved to Person.<p>
 *
 * Når en bruker logger på vil det lages et nytt Authentication object, som inneholder 
 * informasjon om brukeren:
 * 	brukernavn
 * 	navn
 * 	 
 * Skal også ha en metode som sier hvorvidt brukeren er autorisert.
 * 
 * 
 * --Etter å ha tenkt litt er det kanskje like greit å bare droppe hele denne klassen,
 * og bare ta utgangspunkt i at brukerautentisering skjer en gang, når man logger inn
 * Synes denne skal slettes --
 * Sindre
 * 
 */

public class Authenication {
	
	private boolean isAuthenicated;
	private String userName;
	
//	Eksempellogin frem til vi får satt opp database	
	final static String user ="Per";
	final static String pass ="123";
	
	public Authenication(){
		isAuthenicated = false;
	}
	
	public void authenticate(String username, String password){
		System.out.println("Username "+username);
		System.out.println("Password "+password);
		System.out.println("user "+user);
		System.out.println("pass "+pass);
// Midlertidig brukergodkjenning, frem til vi får satt opp tilkobling til database
		if(username==user)
			if (password==pass){
				isAuthenicated=true;
				userName = username;
			}
		else
			isAuthenicated=false;
	}

//	Returnerer om en bruker er logget inn eller ikke.	
	public boolean getAuthenicationStatus(){
		return isAuthenicated;
	}
	public String getUserName(){
		return userName;
	}
	
}