package gui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

import util.ChangeType;
import util.GuiListener;

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

	LoginPanel loginPanel;
	CalendarPanel calendarPanel;
	EditPanel editPanel;
	NewPanel newPanel;
	DeletePanel deletePanel;

	private User user;
	private Boolean loggedIn;

	
	public ProjectPanel()
	{
		loggedIn = false;
		changePanel("");
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
			loginPanel = new LoginPanel();
			loginPanel.addGuiListener(this);
			add(loginPanel);
		}
		else if(panel.equals("calendar")){
			System.out.println("adding JLabel");
			calendarPanel = new CalendarPanel();
			add(calendarPanel);
			//add(new JLabel("Logget inn"));
			repaint();
			revalidate();
		}
	}

	@Override
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		if(ct == ChangeType.LOGIN){
			
			user = new User();
			
			System.out.println("btn pushed");
			user = new User();
			
			if(user.validateLogin((String)list.get(0),(String)list.get(1))){
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
