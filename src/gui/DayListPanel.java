package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.*;


/**
 * Help class for rendering appointments in the WeeklyCalendarPanel
 * @author jorgen
 *
 */
public class DayListPanel extends JPanel{

	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private GridBagConstraints g;
	

	
	public DayListPanel(){
		g = new GridBagConstraints();
		g.gridy = 0;
		g.weightx = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		
	}
	
	public void addButton(JButton b){
		buttons.add(b);
		
		add(buttons.get(buttons.size()-1), g);
		g.gridy += 1;
		

	}
	
	public void clearList(){
		buttons = new ArrayList<JButton>();
		removeAll();
	}
}
