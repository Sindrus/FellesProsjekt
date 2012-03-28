package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import util.ChangeType;
import util.GUIListener;
import util.GUIListenerSupport;
import model.*;

public class MeetingPanel extends JPanel{
	
	JList personList, romList;
	JButton backBtn, createBtn;
	DefaultListModel defaultPersonListModel, defaultRomListModel;
	JPanel innerPanel, btnPanel;
	
	GUIListenerSupport gls;
	
	//Dummyvalues
	User user1,user2,user3;
	Room room1,room2,room3;
	private ArrayList<Room> rooms;
	
	/**
	 * Constructor for the <code>NewMeetingPanel</code> that creates all the JObjects
	 */
	public MeetingPanel(){
		
		gls = new GUIListenerSupport();
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.CENTER;
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;
		
		
		//	innerPanel
		g.anchor = GridBagConstraints.NORTHWEST;
		g.insets = new Insets(5, 5, 5, 5);
		g.gridx=0;
		g.gridy=0;

		innerPanel = new JPanel(new GridBagLayout());

		
		
		// Personvelger
		innerPanel.add(new JLabel("Velg deltakere: "),g);
		
		defaultPersonListModel = new DefaultListModel();
		

	
		personList = new JList(defaultPersonListModel);
		personList.setCellRenderer(new PersonListRenderer());
		personList.setVisibleRowCount(5);
		personList.setFixedCellWidth(250);
		personList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		g.gridx=1;
		innerPanel.add(personList,g);
		
		JScrollPane personScroll = new JScrollPane(personList);
		personScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		personScroll.setMinimumSize(new Dimension(300,100));
		innerPanel.add(personScroll,g);
		
		g.gridx=0;
		g.gridy=1;
	
		
		
		// Romvelger
		innerPanel.add(new JLabel("Reserver rom: "),g);
		
		defaultRomListModel = new DefaultListModel();
		
		

		
		romList = new JList(defaultRomListModel);
		romList.setCellRenderer(new RoomListRenderer());
		romList.setVisibleRowCount(5);
		romList.setFixedCellWidth(250);
		romList.setFixedCellHeight(25);
		
		romList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		g.gridx=1;
		innerPanel.add(romList,g);
		
		JScrollPane romScroll = new JScrollPane(romList);
		romScroll.setMinimumSize(new Dimension(300,100));
		romScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		innerPanel.add(romScroll,g);
		
//	end innerPanel
		g.gridy=0;
		add(innerPanel,g);
		
//	btnPanel
		btnPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints btng = new GridBagConstraints();
		btng.anchor = GridBagConstraints.CENTER;
		btng.insets = new Insets(0,30,0,30);
		btng.gridx=0;
		btng.gridy=0;
		
		createBtn = new JButton("Fullfør");
		createBtn.addActionListener(new createAction());
		btng.gridx=0;
		btnPanel.add(createBtn,btng);
		
		backBtn = new JButton("Tilbake");
		backBtn.addActionListener(new backAction());
		btng.gridx=1;
		btnPanel.add(backBtn,btng);
		
//	end btnPanel
		g.gridy=1;
		add(btnPanel,g);
		
		
// Adding dummy rooms and users
//		addDummyValues();
		
	}
	
	public void setRooms(ArrayList<Room> r){
		rooms = r;
	}
	
	public void fillLists(ArrayList<Room> r, ArrayList<User> u){
		System.out.println("Gønner på");
		
		for (int i = 0; i < u.size(); i++) {
			defaultPersonListModel.addElement(u.get(i));
		}
		
		for (int i = 0; i < r.size(); i++) {
			defaultRomListModel.addElement(r.get(i));
		}
	}
	
	/**
	 * Returns the roomnumber of the selected room
	 * @return roomnumber as <code>int</code>
	 */
	public int getRoomNumber(){
		if(romList.getSelectedIndex()==-1)
			return 0;
		Room selectedRoom = (Room)defaultRomListModel.getElementAt(romList.getSelectedIndex());
		return selectedRoom.getRoomNumber(); 
	}
	
	/**
	 * Returns the <code>Room</code> that is selected
	 * @return <code>Room</code> object
	 */
	public Room getRoom(){
		if(romList.getSelectedIndex()==-1){
			return null;
		}
		return (Room)defaultRomListModel.getElementAt(romList.getSelectedIndex());
	}
	
	/**
	 * Returns a list of selected <code>User</code> objects as an ArrayList 
	 * @return list of <code>User</code>
	 */
	public ArrayList<Object> getSelectedParticipants(){
		ArrayList<Object> participantList = new ArrayList<Object>();
		for(int i=0;i<personList.getSelectedIndices().length;i++)
			participantList.add(defaultPersonListModel.getElementAt(i));
		return participantList;
	}
	
	/**
	 * Return a list of the selected participants
	 * @return A list of the <code>User</code> objects that is selected
	 */
	public User[] getParticipants(){
		int length = personList.getSelectedIndices().length;
		User[] parList = new User[length];
		for(int i=0;i<length;i++){
			parList[i]=(User)defaultPersonListModel.get(i);
		}
		return parList;
	}
	
	/**
	 * 
	 * Classes for btn action
	 *
	 */
	class backAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ArrayList<Object> array = new ArrayList<Object>();
			gls.notifyListeners(ChangeType.BACK, array);
		}
	}
	class createAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Object> array = new ArrayList<Object>();
			for(Object i : getSelectedParticipants())
				array.add(i);
			System.out.println("Inviterte: "+array);
			gls.notifyListeners(ChangeType.CREATEMEETING, array);
		}
	}
	
	public void addGuiListener(GUIListener listener){
		gls.add(listener);
	}
	
//	public void addDummyValues(){
//		
//		user1 = new User("sindre", "sindrus");
//		defaultPersonListModel.addElement(user1);
//		user2 = new User("petter", "pettern");
//		defaultPersonListModel.addElement(user2);
//		user3 = new User("christer", "chris");
//		defaultPersonListModel.addElement(user3);
//		
//		room1 = new Room(1, 4);
//		defaultRomListModel.addElement(room1);
//		room2 = new Room(2, 5);
//		defaultRomListModel.addElement(room2);
//		room3 = new Room(3, 10);
//		defaultRomListModel.addElement(room3);
//		
//	}

}
