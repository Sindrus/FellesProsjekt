package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

import util.ChangeType;
import util.GuiListener;

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

public class ProjectPanel extends JPanel implements GuiListener{

	LoginPanel login;

	private User person;
	private Boolean loggedIn;



	public ProjectPanel()
	{
		loggedIn = false;
		login = new LoginPanel();
		login.addGuiListener(this);
		add(login);
	}


	/**
	 * Changes the content of ProjectPanel to the specified JPanel.<p>
	 * 
	 * @param panel
	 * <p>"calendar" opens the CalendarPanel.
	 */
	public void changePanel(String panel){

		System.out.println("removing all");
		removeAll();


		if (!loggedIn){
			System.out.println("Not logged in");
			login = new LoginPanel();
			login.addGuiListener(this);
			add(login);
		}
		else{
			if(panel.equals("calendar")){
				System.out.println("adding JLabel");
				add(new JLabel("Logget inn"));
				repaint();
				revalidate();
			}
		}
	}



	@Override
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		if(ct == ChangeType.LOGIN){
<<<<<<< HEAD
			person = new Person();
=======
			System.out.println("btn pushed");
			person = new User();
>>>>>>> c76cbfd5e715daac6be6c91aab9e5310d3e7a8f3

			if(person.validateLogin((String)list.get(0),(String)list.get(1))){
				System.out.println("logged in");
				loggedIn= true;
				changePanel("calendar");
			}
			else{
				System.out.println("did not match");
			}
		}
	}






}


