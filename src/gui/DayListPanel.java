package gui;

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
	private GridBagConstraints g = new GridBagConstraints();

	private  int y = 0;
	
	public DayListPanel(){
		setLayout(new GridBagLayout());

		
	}
	
	public void addButton(JButton b){
		buttons.add(b);
		g.gridy = y;
		add(buttons.get(buttons.size()-1), g);

		
		
		y += 1;
	}
	
	public void clearList(){
		buttons = new ArrayList<JButton>();
		removeAll();
	}
}
