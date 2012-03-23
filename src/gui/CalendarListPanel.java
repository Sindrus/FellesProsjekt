package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import util.GUIListenerSupport;

/**
 * 
 * @author Even
 *	Panelet som inneholder liste over andres/egen kalender(e)
 *
 */

public class CalendarListPanel extends JPanel {

	private JPanel calendarPanel;
	private JList calendarList;
	private DefaultListModel defaultPersonListModel;

	public CalendarListPanel(){

		GUIListenerSupport gls = new GUIListenerSupport();
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;
		
		GridBagConstraints calg = new GridBagConstraints();
		calg.anchor = GridBagConstraints.NORTH;
		calg.insets = new Insets(5, 5, 5, 5);
		calg.gridx=0;
		calg.gridy=0;
		
		calendarPanel = new JPanel(new GridBagLayout());
		
		defaultPersonListModel = new DefaultListModel();
		calendarList = new JList(defaultPersonListModel);
		calendarList.setVisibleRowCount(5);
		calendarList.setFixedCellWidth(140);
		calendarList.setFixedCellHeight(40);
		calendarList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		calg.gridy=1;
		calendarPanel.add(calendarList,calg);
		
		JScrollPane personScroll = new JScrollPane(calendarList);
		personScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		calendarPanel.add(personScroll,calg);
		
		g.gridy=0;
		add(calendarPanel,g);
		
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new CalendarListPanel());
		frame.pack();
		frame.setVisible(true);
	}
}