package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Person;
import database.*;

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

public class ProjectPanel extends JPanel implements PropertyChangeListener{

	LoginPanel login;

	private Person person;
	private Boolean loggedIn = false;
	final static String LOGINBTNPUSHED="LoginBtnPushed";
	
	public ProjectPanel()
	{
		login = new LoginPanel();
		login.addPropertyChangeListener(this);
		add(login);
	}
	// Metode for å endre utseende til panele når det trengs.	
	public void rebuildProjectPanel(){
		try {
			// Husk å ta bort gamle paneler	
			removeAll();
		} catch (Exception e) {}
		if (!loggedIn){
			login = new LoginPanel();
			login.addPropertyChangeListener(this);
			add(login);
		}
		else{
			add(new JLabel("Logget inn"));
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName()==LOGINBTNPUSHED){
			System.out.println("btn pushed");
			person = new Person();
			if(person.validateLogin(evt.getOldValue().toString(), evt.getNewValue().toString())){
				loggedIn= true;
				rebuildProjectPanel();
			}
		}
	}






}


