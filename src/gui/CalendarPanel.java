package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import model.Appointment;

import util.ChangeType;
import util.GUIListener;
import util.GUIListenerSupport;

/**
 * 
 * @author sindre
 * 
 * Skal inneholde 3 paneler: Innkalinger, ukekalender og kalenderliste
 * Skal kunne lage en ny JFrame som inneholder et varselpanel
 * 
 * Bygger Kalender viewet som inneholder ukekalender, kalenderliste og liste over innkallinger. Sender all sin informasjon til GUIController
 * 
 *  CalendarListPanel
 *  WeeklyCalendarPanel
 *  InvitesPanel
 * 
 */

public class CalendarPanel extends JPanel implements GUIListener, ActionListener{

	JButton newap, logout, vars, close;
	private JPanel btntopleftPanel, btntoprightPanel,listPanel,btnbtmPanel;
	LoginPanel loginPanel;
	NewPanel newPanel;
	JFrame f;
	GridBagConstraints g, btntopleftg, btntoprightg, lisg, btnbtmg;
	private Toolkit tool = Toolkit.getDefaultToolkit();
	public WeeklyCalendarPanel wp;
	GUIListenerSupport gls;
	private DefaultListModel newModel;

	public CalendarPanel(){

		setBackground(Color.PINK);
		gls = new GUIListenerSupport();

		g = new GridBagConstraints();
		setLayout(new GridBagLayout());
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;

		//New Appointment.
		btntopleftPanel = new JPanel();
		btntopleftg = new GridBagConstraints();

		newap = new JButton("Ny Avtale");

		//end the AppointmentButton.

		//Logout.
		btntoprightPanel = new JPanel();
		btntoprightg = new GridBagConstraints();

		logout = new JButton("Log ut");
		//finish logout.

		//CalendarListPanel and InvitationListPanel
		listPanel = new JPanel(new GridBagLayout());
		lisg = new GridBagConstraints();
		lisg.insets = new Insets(5, 5, 5, 5);

		//end the two lists.

		//WeeklyCalendar

		//Bottom Button Panel
		btnbtmPanel = new JPanel(new GridBagLayout());
		btnbtmg = new GridBagConstraints();

		vars = new JButton("Varslinger");

		//Varslinger.
		buildCalendarPanel();
	}

	
	
	
	
	
	public void buildCalendarPanel(){
		//New Appointment.



		btntopleftg.anchor = GridBagConstraints.NORTHWEST;

		btntopleftg.gridx = 0;
		btntopleftg.gridy = 0;
		newap.addActionListener(this);
		newap.setBackground(Color.GREEN);
		btntopleftPanel.add(newap,btntopleftg);

		//end the AppointmentButton.

		g.gridx = 0;
		g.gridy = 0;
		add(btntopleftPanel, g);

		//Logout.
		btntoprightg.anchor = GridBagConstraints.NORTHEAST;

		btntoprightg.gridx = 0;
		btntoprightg.gridy = 0;
		logout.addActionListener(this);
		btntoprightPanel.add(logout,btntopleftg);
		//finish logout.

		g.gridx = 2;
		g.gridy = 0;
		add(btntoprightPanel, g);


		//CalendarListPanel and InvitationListPanel
		lisg.anchor = GridBagConstraints.NORTHWEST;

		lisg.gridx = 0;
		lisg.gridy = 0;
		listPanel.add(new CalendarListPanel(), lisg);

		lisg.gridx = 0;
		lisg.gridy = 1;
		listPanel.add(new InvitationListPanel(), lisg);

		//end the two lists.
		g.gridx = 0;
		g.gridy = 1;
		listPanel.setPreferredSize(new Dimension((int)tool.getScreenSize().getWidth()/7, (int)(tool.getScreenSize().getHeight()/(1.5))));
		add(listPanel, g);

		//WeeklyCalendar
		g.gridx = 1;
		g.gridy = 1;
		wp = new WeeklyCalendarPanel();
		wp.setPreferredSize(new Dimension((int)(tool.getScreenSize().getWidth()/(1.6)), (int)(tool.getScreenSize().getHeight()/(1.3))));
		add(wp, g);
		//finish.

		//Bottom Button Panel
		btntoprightg.anchor = GridBagConstraints.SOUTHWEST;

		btntoprightg.gridx = 0;
		btntoprightg.gridy = 0;
		vars.addActionListener(this);
		btnbtmPanel.add(vars,btnbtmg);

		//Varslinger.
		g.gridx = 0;
		g.gridy = 2;
		add(btnbtmPanel, g);

		revalidate();
	}

	
	
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		if (ct == ChangeType.BACK || ct == ChangeType.CREATEMEETING){
			removeAll();

		}
	}

	
	
	
	public void addGuiListener(GUIListener listener){
		gls.add(listener);
	}

	

	
	
	public void actionPerformed(ActionEvent e) {


		if(e.getSource()==newap){
			System.out.println("new Appointment");
			gls.notifyListeners(ChangeType.NEWAPP, null);

		}
		else if(e.getSource()==logout){
			System.out.println("Logout");
			gls.notifyListeners(ChangeType.LOGOUT, null);
		}

		
		
		
		
		//Dette trenger egen klasse
		
		else if(e.getSource()==vars){

			f = new JFrame("Notifications");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
			f.setVisible(true);

			JPanel ntf = new JPanel();  //creates a Jpanel and sets layout.
			GridBagConstraints g = new GridBagConstraints();
			g.gridx = 0;
			g.gridy = 0;
			g.anchor = GridBagConstraints.CENTER;
			ntf.setLayout(new GridBagLayout());

			newModel = new DefaultListModel(); //List model (to be replaced with DB)
			newModel.addElement("First Notification");
			newModel.addElement("Second Notification");
			JList list = new JList(newModel);
			ntf.add(list,g);	

			g.gridy = 1;  // Close Button.
			close = new JButton("Lukk");
			close.addActionListener(new Close());
			ntf.add(close,g);

			f.add(ntf);

		}
	}
	class Close implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			f.dispose();
		}
	}

	//	public static void main(String args[]) {
	//		JFrame frame = new JFrame("Kalender");
	//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//		frame.add(new CalendarPanel());
	//		frame.pack();
	//		frame.setVisible(true);
	//	}

}
