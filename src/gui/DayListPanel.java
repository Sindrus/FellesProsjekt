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


	private  int y = 0;
	
	public DayListPanel(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Color.BLACK);
		
	}
	
	public void addButton(JButton b){
		buttons.add(b);

		add(buttons.get(buttons.size()-1));

		
		y += 1;
	}
	
	public void clearList(){
		buttons = new ArrayList<JButton>();
		removeAll();
	}
}
