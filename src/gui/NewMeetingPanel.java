package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * 
 * @author sindre
 * 
 * Panelet som gjør en avtale om til et møte
 * 
 */

public class NewMeetingPanel extends JPanel{
	
	JList personList, romList;
	JButton backBtn, createBtn;
	DefaultListModel defaultPersonListModel, defaultRomListModel;
	JPanel innerPanel, btnPanel;
	
	public NewMeetingPanel(){

		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;
		
//	innerPanel
		GridBagConstraints parg = new GridBagConstraints();
		parg.anchor = GridBagConstraints.NORTHWEST;
		parg.insets = new Insets(5, 5, 5, 5);
		parg.gridx=0;
		parg.gridy=0;
		
		innerPanel = new JPanel(new GridBagLayout());
		
		innerPanel.add(new JLabel("Velg deltakere: "),parg);
		

		defaultPersonListModel = new DefaultListModel();
		personList = new JList(defaultPersonListModel);
		personList.setVisibleRowCount(5);
		personList.setFixedCellWidth(300);
		personList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		parg.gridx=1;
		innerPanel.add(personList,parg);
		
		JScrollPane personScroll = new JScrollPane(personList);
		personScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		innerPanel.add(personScroll,parg);
		
		parg.gridx=0;
		parg.gridy=1;
		
		innerPanel.add(new JLabel("Reserver rom: "),parg);
		
		defaultRomListModel = new DefaultListModel();
		romList = new JList(defaultRomListModel);
		romList.setVisibleRowCount(5);
		romList.setFixedCellWidth(200);
		romList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		parg.gridx=1;
		innerPanel.add(romList,parg);
		
		JScrollPane romScroll = new JScrollPane(romList);
		romScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		innerPanel.add(romScroll,parg);
		
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
		
	}
	
	class backAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	class createAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setSize(700,700);
		frame.add(new NewMeetingPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
