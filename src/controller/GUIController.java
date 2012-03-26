package controller;



import gui.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import model.User;
import util.ChangeType;
import util.GUIListener;


/**
 * This class controls the GUI, and takes input from changes in the Model. 
 * @author jorgen
 *
 */
public class GUIController implements GUIListener{

	private ProjectPanel pp;
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private boolean loggedIn = false;
	private LoginPanel loginPanel;
	private CalendarPanel calendarPanel;
	private NewPanel newPanel;
	private User user;

	public GUIController(){
		System.out.println("Starting GuiController");

		startPanels();

		JFrame jf = new JFrame("Kalender");
		jf.setLayout(new GridBagLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jf.setContentPane(pp);
		jf.getContentPane().setPreferredSize(new Dimension((int)tool.getScreenSize().getWidth(), (int)(tool.getScreenSize().getHeight())));
		jf.getContentPane().setBackground(Color.BLUE);
		jf.setVisible(true);

		changePanel("");
		System.out.println("GuiController made");
	}

	private void startPanels(){
		System.out.println("Initializing panels");

		pp = new ProjectPanel();

		loginPanel = new LoginPanel();
		loginPanel.addGuiListener(this);

		calendarPanel = new CalendarPanel();
		calendarPanel.addGuiListener(this);

		System.out.println("Panels initialized");
	}


	/**
	 * Changes the content of ProjectPanel to the specified JPanel.<p>
	 * 
	 * @param panel
	 * <p>"calendar" opens the CalendarPanel.
	 */
	public void changePanel(String panel){

		pp.removeAll();

		if (!loggedIn){
			System.out.println("not logged in");
			loginPanel.setPreferredSize(new Dimension((int)tool.getScreenSize().getWidth()/3, (int)(tool.getScreenSize().getHeight()/3)));
			pp.add(loginPanel);

		}

		else if(panel.equals("calendar")){
			System.out.println("adding calendar");
			//calendarPanel.setMinimumSize(new Dimension(3000, 3000));
			calendarPanel.setPreferredSize(new Dimension((int)(tool.getScreenSize().getWidth()-20), (int)((tool.getScreenSize().getHeight()))- 40));
			//calendarPanel.setMaximumSize(new Dimension(3000, 3000));
			pp.add(calendarPanel);

		}

		else if(panel.equals("logout")){
			System.out.println("adding loginPanel");
			loggedIn = false;
			pp.add(loginPanel);

		}

		else if (panel.equals("newapp")){
			System.out.println("adding newPanel");
			newPanel = new NewPanel();
			newPanel.setPreferredSize(new Dimension((int)tool.getScreenSize().getWidth() - 40, (int)(tool.getScreenSize().getHeight()-40)));
			newPanel.addGuiListener(this);
			pp.add(newPanel);
		}

		else{
			System.out.println("panel did not match");
			pp.add(calendarPanel);

		}
		
		pp.validate();
		pp.revalidate();
		pp.repaint();


	}

	@Override
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		System.out.println("Notifying GUI: " + ct);

		if(list != null){

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}


		if(ct == ChangeType.LOGIN){
			System.out.println("Ct = login");
			user = new User();

			if(user.validateLogin((String)list.get(0),(String)list.get(1))){
				System.out.println("logged in");
				loggedIn= true;
				changePanel("calendar");
			}
		}

		else if(ct == ChangeType.LOGOUT){
			System.out.println("ct = logout");
			user = null;

			changePanel("logout");

		}
		
		else if(ct == ChangeType.NEWAPP){
			System.out.println("ct = newApp");
			changePanel("newapp");
			
		}
		
		else{
			System.out.println("ChangeType not recognized");

		}
	}


}
