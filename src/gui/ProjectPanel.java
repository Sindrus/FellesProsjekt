package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.ChangeType;
import util.GuiListener;

import Model.Person;
import database.*;

/**
 * 
 * Dette vil være selve driveren til guiet.
 * Her ligger mainmetoden.
 *
 */

public class ProjectPanel extends JPanel implements GuiListener{

	LoginPanel login;

	private Person person;
	private Boolean loggedIn = false;



	public ProjectPanel()
	{
		login = new LoginPanel();
		login.addGuiListener(this);
		add(login);
	}

	
	public void rebuildProjectPanel(){
		try {
			System.out.println("removing all");
			removeAll();
		} catch (Exception e) {}
		
		if (!loggedIn){
			System.out.println("Not logged in");
			login = new LoginPanel();
			login.addGuiListener(this);
			add(login);
		}
		else{
			System.out.println("adding JLabel");
			add(new JLabel("Logget inn"));
			repaint();
			revalidate();
		}
	}


//	public void propertyChange(PropertyChangeEvent evt) {
//		if(evt.getPropertyName()==LOGINBTNPUSHED){
//			System.out.println("btn pushed");
//			person = new Person();
//			if(person.validateLogin(evt.getOldValue().toString(), evt.getNewValue().toString())){
//				System.out.println("logged in");
//				loggedIn= true;
//				rebuildProjectPanel();
//			}
//			else{
//				System.out.println("did not match");
//			}
//		}
//	}
	@Override
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		if(ct == ChangeType.LOGIN){
			System.out.println("btn pushed");
			person = new Person();

			if(person.validateLogin((String)list.get(0),(String)list.get(1))){
				System.out.println("logged in");
				loggedIn= true;
				rebuildProjectPanel();
			}
			else{
				System.out.println("did not match");
			}
		}
	}






}


