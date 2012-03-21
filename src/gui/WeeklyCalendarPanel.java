package gui;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author sindre
 * 
 * Panelet som inneholder den ukentlige kalendern.
 * 
 */

public class WeeklyCalendarPanel extends JPanel {
	
	static Calendar c = Calendar.getInstance();
	static String[] weekdays = {"", "Sondag", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lordag"};
	static String[] monthnames = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "Semptember", "Oktober", "November", "Desember"};
	static int weeknum = c.get(Calendar.WEEK_OF_YEAR);
	static int daynum = c.get(Calendar.DAY_OF_MONTH);
	static int monthnum = Calendar.MONTH+1;
	JButton Left,Right;
	GridBagConstraints g = new GridBagConstraints();
	JLabel day[] = new JLabel[7];
	JLabel ukenummer = new JLabel();
	
	
	
	public WeeklyCalendarPanel(){
		
		setLayout(new GridBagLayout());
		g.gridy = 0;
		Left = new JButton("<=");
		add(Left,g);
		Right = new JButton("=>");
		Right.addActionListener(new Right());
		add(Right,g);
		
		g.gridy = 1;
		ukenummer = new JLabel("Ukenummer: " + Integer.toString(weeknum) + "            ");
		add (ukenummer,g);
		
		for (int i = 0; i<7; i++){
			if (daynum+i > 28 && monthnum == 2)
			{
				monthnum = monthnum + 1;
				if (i == 1)
				{
					daynum = 0;
				}
				if (i == 2)
				{
					daynum = -1;
				}
				if (i == 3)
				{
					daynum = -2;
				}
				if (i == 4)
				{
					daynum = -3;
				}
				if (i == 5)
				{
					daynum = -4;
				}
				if (i == 6)
				{
					daynum = -5;
				}
			}
			if (daynum+i > 30 && (monthnum == 4 || monthnum == 6 || monthnum == 9 || monthnum == 11) )
			{
				if (i == 1)
				{
					daynum = 0;
				}
				monthnum = monthnum + 1;
				if (i == 2)
				{
					daynum = -1;
				}
				if (i == 3)
				{
					daynum = -2;
				}
				if (i == 4)
				{
					daynum = -3;
				}
				if (i == 5)
				{
					daynum = -4;
				}
				if (i == 6)
				{
					daynum = -5;
				}
			}
			if (daynum+i > 31 && (monthnum == 1 || monthnum == 3 || monthnum == 5 || monthnum == 7 || monthnum == 8 || monthnum == 10 || monthnum == 12) )
			{
				monthnum = monthnum + 1;
				if (i == 1)
				{
					daynum = 0;
				}
				if (i == 2)
				{
					daynum = -1;
				}
				if (i == 3)
				{
					daynum = -2;
				}
				if (i == 4)
				{
					daynum = -3;
				}
				if (i == 5)
				{
					daynum = -4;
				}
				if (i == 6)
				{
					daynum = -5;
				}
			}
			if (c.get(Calendar.DAY_OF_WEEK)+i <= 7 ){
			day[i] = new JLabel(weekdays[c.get(Calendar.DAY_OF_WEEK)+i] + "." + (daynum+i) + "." + monthnum + "           ");
			add(day[i],g);
			}
			else{
			int h = c.get(Calendar.DAY_OF_WEEK)+ i - 7;
			day[i] = new JLabel(weekdays[h] + "." + (daynum+i) + "." + monthnum + "           ");
			add(day[i],g);
			}
		}
		
	}
	
	class Right implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i<7; i++){
				remove(day[i]);
				validate();
			}
			
			if (daynum + 7 < 31 && (monthnum == 1 || monthnum == 3 || monthnum == 5 || monthnum == 7 || monthnum == 8 || monthnum == 10 || monthnum == 12)){
				daynum = daynum + 7;}
				else if (daynum + 6 == 31 && (monthnum == 1 || monthnum == 3 || monthnum == 5 || monthnum == 7 || monthnum == 8 || monthnum == 10 || monthnum == 12)){
				daynum = 1;
				monthnum = monthnum+1;}
				else if (daynum + 7 == 31 && (monthnum == 1 || monthnum == 3 || monthnum == 5 || monthnum == 7 || monthnum == 8 || monthnum == 10 || monthnum == 12)){
				daynum = 31;}
			if (daynum + 7 < 30 && (monthnum == 4 || monthnum == 6 || monthnum == 9 || monthnum == 11)){
				daynum = daynum + 7;}
				else if (daynum + 6 == 30 && (monthnum == 4 || monthnum == 6 || monthnum == 9 || monthnum == 11)){
				daynum = 1;
				monthnum = monthnum+1;}
			if (daynum + 7 < 28 && (monthnum == 2)){
				daynum = daynum + 7;}
				else if (daynum + 6 == 28 && (monthnum == 2)){
				daynum = 1;
				monthnum = monthnum+1;}
			
			weeknum = weeknum +1;
			
			if (monthnum == 13)
			{
				monthnum = 1;
				weeknum = 1;
			}
			
			remove(ukenummer);
			validate();
			ukenummer = new JLabel("Ukenummer: " + Integer.toString(weeknum) + "            ");
			add (ukenummer,g);
			validate();
			
			for (int i = 0; i<7; i++){
				if (daynum+i > 28 && monthnum == 2)
				{
					monthnum = monthnum + 1;
					if (i == 1)
					{
						daynum = 0;
					}
					if (i == 2)
					{
						daynum = -1;
					}
					if (i == 3)
					{
						daynum = -2;
					}
					if (i == 4)
					{
						daynum = -3;
					}
					if (i == 5)
					{
						daynum = -4;
					}
					if (i == 6)
					{
						daynum = -5;
					}
				}
				if (daynum+i > 30 && (monthnum == 4 || monthnum == 6 || monthnum == 9 || monthnum == 11) )
				{
					if (i == 1)
					{
						daynum = 0;
					}
					monthnum = monthnum + 1;
					if (i == 2)
					{
						daynum = -1;
					}
					if (i == 3)
					{
						daynum = -2;
					}
					if (i == 4)
					{
						daynum = -3;
					}
					if (i == 5)
					{
						daynum = -4;
					}
					if (i == 6)
					{
						daynum = -5;
					}
				}
				if (daynum+i > 31 && (monthnum == 1 || monthnum == 3 || monthnum == 5 || monthnum == 7 || monthnum == 8 || monthnum == 10 || monthnum == 12) )
				{
					if (monthnum + 1 == 13){
						monthnum = 1;
						weeknum = 1;
						}
					else
					monthnum = monthnum +1;
					if (i == 1)
					{
						daynum = 0;
					}
					if (i == 2)
					{
						daynum = -1;
					}
					if (i == 3)
					{
						daynum = -2;
					}
					if (i == 4)
					{
						daynum = -3;
					}
					if (i == 5)
					{
						daynum = -4;
					}
					if (i == 6)
					{
						daynum = -5;
					}
				}
				if (c.get(Calendar.DAY_OF_WEEK)+i <= 7 ){
				day[i] = new JLabel(weekdays[c.get(Calendar.DAY_OF_WEEK)+i] + "." + (daynum+i) + "." + monthnum + "           ");
				add(day[i],g);
				}
				else{
				int h = c.get(Calendar.DAY_OF_WEEK)+ i - 7;
				day[i] = new JLabel(weekdays[h] + "." + (daynum+i) + "." + monthnum + "           ");
				add(day[i],g);
				}
				validate();
			}
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new WeeklyCalendarPanel());
		frame.pack();
		frame.setVisible(true);
	}
}

