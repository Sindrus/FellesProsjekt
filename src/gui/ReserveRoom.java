package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ReserveRoom extends JPanel {

	JButton Tilbake, Full;
	JTextField Who;
	JList RomList;
	JComboBox Rom;
	PropertyChangeSupport pcs;

	public ReserveRoom(){
		
		String[] p = new String[21];
		for (int i=0;i<=20;i++)
		{
			p[i]=Integer.toString(i);
		}
		
		pcs = new PropertyChangeSupport(this);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=0;
		JLabel d = new JLabel("Deltakere:");
		add(d,g);
		Who = new JTextField(""); 
		Font f = new Font(Who.getFont().getName(),Who.getFont().getStyle(),14);
		Who.setColumns(20);
		Who.setFont(f);
		add(Who,g);

		g.anchor = GridBagConstraints.ABOVE_BASELINE;
		g.gridy = 0;
		JLabel Antall = new JLabel("Antall:");
		add(Antall,g);
		Rom = new JComboBox(p);
		add(Rom,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=1;
		JLabel r = new JLabel("Reserver rom:");
		add(r,g);
		
		RomList = new JList();
		RomList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		RomList.setLayoutOrientation(JList.VERTICAL);
		RomList.setVisibleRowCount(-1);
		RomList.setBorder(BorderFactory.createLoweredBevelBorder());
		Object[] listData = new Object[20]; //List will come from the database, this is just for testing purposes.
		for (int i=0; i<=19; i++)
		{
			listData[i] = "Room " + i;
		}
		RomList.setListData(listData);
		JScrollPane ScrollBar = new JScrollPane(RomList);
		ScrollBar.setPreferredSize(new Dimension(250, 80));
		add(ScrollBar,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=2;
		Tilbake = new JButton("Tilbake");
		Tilbake.addActionListener(new Godta());
		add(Tilbake,g);
		
		g.anchor = GridBagConstraints.EAST;
		g.gridy=2;
		Full = new JButton("Fullført");
		Full.addActionListener(new Avvis());
		add(Full,g);
	}
	
	
	class Godta implements ActionListener { //no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class Avvis implements ActionListener { //no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new ReserveRoom());
		frame.pack();
		frame.setVisible(true);
	}
}
