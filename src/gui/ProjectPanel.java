package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Authenication;

/**
 * 
 * Dette vil være selve driveren til guiet.
 * Her ligger mainmetoden.
 *
 */

public class ProjectPanel extends JPanel implements PropertyChangeListener{
	
	LoginPanel login;
	Authenication auth;
	
	final static String LOGINBTNPUSHED="LoginBtnPushed";
	
	public ProjectPanel(){
		auth = new Authenication();
		rebuildProjectPanel();
	}
// Metode for å endre utseendet til panelet når det trengs.	
	public void rebuildProjectPanel(){
		try {
		// Husk å ta bort gamle paneler	
			remove(login);
		} catch (Exception e) {}
		if (!auth.getAuthenicationStatus()){
			login = new LoginPanel();
			login.addPropertyChangeListener(this);
			add(login);
		}
		else{
			add(new JLabel("Logget inn"));
		}
	}
	
	public static void main(String[] args){
		
		JFrame jf = new JFrame("Kalender");
		ProjectPanel pp = new ProjectPanel();
		jf.setLayout(new GridBagLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;
		
		jf.add(pp, g);
		jf.pack();
		jf.setSize(600, 400);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==LOGINBTNPUSHED){
		//Har problemer med at ikke passordet kommer hit i som text, men som object.	
			System.out.println("btn pushed");
		//Her må det endres om vi skal droppe authenticate klassen.
			auth.authenticate(evt.getOldValue().toString(), evt.getNewValue().toString());
			System.out.println(auth.getAuthenicationStatus());
		}
	}
	
}
