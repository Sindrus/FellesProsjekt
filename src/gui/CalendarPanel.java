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
	private JPanel listPanel;
	LoginPanel loginPanel;
	NewPanel newPanel;
	JFrame f;
	GridBagConstraints g;
	private Toolkit tool = Toolkit.getDefaultToolkit();
	public WeeklyCalendarPanel wp;
	GUIListenerSupport gls;
	private DefaultListModel newModel;

	public CalendarPanel(){

		setBackground(GConfig.CALENDARCOLOR);
		
		gls = new GUIListenerSupport();

		
		g = new GridBagConstraints();
		setLayout(new GridBagLayout());
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;
		g.fill = GridBagConstraints.BOTH;
	
		
		newap = new JButton("Ny Avtale");
		logout = new JButton("Log ut");
		vars = new JButton("Varslinger");

		listPanel = new JPanel(new GridBagLayout());

		listPanel.setBackground(GConfig.LISTCOLOR);
		vars.setBackground(GConfig.VARSELCOLOR);
		logout.setBackground(GConfig.LOGOUTCOLOR);

		buildCalendarPanel();
	}

	
	
	
	
	
	public void buildCalendarPanel(){
		
		//New Appointment.
		g.anchor = GridBagConstraints.NORTHWEST;
		g.gridx = 0;
		g.gridy = 0;
		newap.addActionListener(this);
		newap.setBackground(Color.GREEN);
		add(newap,g);
		//end the AppointmentButton.


		//Logout.
		g.anchor = GridBagConstraints.NORTHEAST;
		g.gridx = 3;
		g.gridy = 0;
		logout.addActionListener(this);
		add(logout,g);
		//finish logout.



		//CalendarListPanel and InvitationListPanel
		g.anchor = GridBagConstraints.NORTHWEST;
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		CalendarListPanel cl = new CalendarListPanel();
		cl.setBackground(GConfig.LISTCOLOR);
		listPanel.add(cl, g);

		g.gridx = 0;
		g.gridy = 1;
		InvitationListPanel il = new InvitationListPanel();
		il.setBackground(GConfig.LISTCOLOR);
		listPanel.add(il, g);
		
		g.gridx = 0;
		g.gridy = 1;
		add(listPanel, g);
		//end the two lists.
		
		
		
		//WeeklyCalendar
		g.gridx = 1;
		g.gridy = 1;
		wp = new WeeklyCalendarPanel();
		wp.setPreferredSize(new Dimension((int)(tool.getScreenSize().getWidth()/(1.6)), (int)(tool.getScreenSize().getHeight()/(1.3))));
		add(wp, g);
		//finish.

		
		//Bottom Button Panel
		g.anchor = GridBagConstraints.SOUTHWEST;
		g.gridx = 0;
		g.gridy = 3;
		vars.addActionListener(this);
		add(vars,g);
		//end bottom panel


		
		revalidate();
	}

	
	
	public void notifyGui(ChangeType ct, ArrayList<Object> list) {
		if (ct == ChangeType.BACK || ct == ChangeType.CREATEMEETING){
			removeAll();

		}
	}

	
	
	
	public void addGuiListener(GUIListener listener){
		gls.add(listener);
		wp.addGuiListener(listener);
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
			close.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					f.dispose();
					
				}
			});
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
