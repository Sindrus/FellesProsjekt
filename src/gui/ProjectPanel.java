package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.ArrayList;
import javax.swing.JPanel;
import model.User;
import util.ChangeType;
import util.GUIListener;

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



public class ProjectPanel extends JPanel implements GUIListener{

	
	
	LoginPanel loginPanel;
	CalendarPanel calendarPanel;
	EditPanel editPanel;
	NewPanel newPanel;

	private User user;
	private Boolean loggedIn;
	private Toolkit tool = Toolkit.getDefaultToolkit();

	
	public ProjectPanel()
	{
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setBackground(Color.GREEN);
		loggedIn = false;

		loginPanel = new LoginPanel();
		loginPanel.addGuiListener(this);
		calendarPanel = new CalendarPanel();
		calendarPanel.addGuiListener(this);
		
		changePanel("");
	}
	
	/**
	 * Changes the content of ProjectPanel to the specified JPanel.<p>
	 * 
	 * @param panel
	 * <p>"calendar" opens the CalendarPanel.
	 */
	public void changePanel(String panel){
		
		removeAll();
		
		if (!loggedIn){
			loginPanel.setPreferredSize(new Dimension((int)tool.getScreenSize().getWidth()/3, (int)(tool.getScreenSize().getHeight()/3)));
			add(loginPanel);
			repaint();
			revalidate();
		}
		else if(panel.equals("calendar")){
			//calendarPanel.setMinimumSize(new Dimension(3000, 3000));
			calendarPanel.setPreferredSize(new Dimension((int)(tool.getScreenSize().getWidth()-20), (int)((tool.getScreenSize().getHeight()))- 40));
			//calendarPanel.setMaximumSize(new Dimension(3000, 3000));
			add(calendarPanel);
			repaint();
			revalidate();
		}else if(panel.equals("logout")){
			add(loginPanel);
			repaint();
			revalidate();
		}else{
			add(calendarPanel);
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
			
			if(user.validateLogin((String)list.get(0),(String)list.get(1))){
				System.out.println("logged in");
				loggedIn= true;
				changePanel("calendar");
			}
		}else if(ct == ChangeType.LOGOUT){
			user = null;
			loggedIn=false;
			changePanel("logout");
			
		}
	}
}
