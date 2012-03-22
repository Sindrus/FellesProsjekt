package gui;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * 
 * @author Jorgen
 * 
 * Panelet som inneholder den ukentlige kalendern.
 * 
 */

public class WeeklyCalendarPanel extends JPanel implements ActionListener{

	static Calendar c = Calendar.getInstance();
	static String[] weekdays = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lordag","Sondag"};
	static String[] monthnames = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "Semptember", "Oktober", "November", "Desember"};
	static int weeknum = c.get(Calendar.WEEK_OF_YEAR);
	static int daynum = c.get(Calendar.DAY_OF_MONTH);
	static int monthnum = Calendar.MONTH+1;
	JButton left,right;
	GridBagConstraints g = new GridBagConstraints();
	String[] day = new String[7];
	JLabel ukenummer = new JLabel();
	JTable weekTable;
	Year y;


	public WeeklyCalendarPanel(){
		y = getYear(Calendar.getInstance().get(Calendar.YEAR));

		setLayout(new GridBagLayout());
		g.gridy = 0;
		g.gridx = 0;
		left = new JButton("<=");
		left.addActionListener(this);
		add(left,g);

		g.gridy = 1;
		right = new JButton("=>");
		right.addActionListener(this);
		add(right,g);

		g.gridy = 2;
		ukenummer = new JLabel("Ukenummer: " + Integer.toString(weeknum) + "            ");
		add (ukenummer,g);

		g.gridy = 3;
		weekTable = new JTable();

		updateWeek();
		add(weekTable, g);

		validate();

	}

	private void updateWeek(){

		DefaultTableModel l = new DefaultTableModel();
		for (int i = 0; i < weekdays.length; i++) {
			String[] day = new String[1];
			Integer[] dm= {weeknum, y.weeks.get(weeknum)[i]};
			System.out.println(weeknum + " " + y.weeks.get(weeknum)[i] + " " + y.dayMonth.get(dm));
			day[0] = weekdays[i] + " " + String.valueOf(y.weeks.get(weeknum)[i]) + ". " + monthnames[y.dayMonth.get(dm)];
			l.addColumn(weekdays[i], day);
		}

		weekTable.setModel(l);
		ukenummer.setText("Ukenummer: " + Integer.toString(weeknum));

		weekTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < weekdays.length; i++) {
			weekTable.getColumnModel().getColumn(i).setPreferredWidth(100);
		}

	}


	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new WeeklyCalendarPanel());
		frame.pack();
		frame.setVisible(true);

	}


	private Year getYear(int year){

		int month = 0;
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);



		ArrayList<int[]> weeks = new ArrayList<int[]>();

		int[] weekDays = new int[7];
		int dayCount = 6;
		int monthCount = -1;
		HashMap<Integer[], Integer > dayMonth = new HashMap<Integer[], Integer>();
		int weekCount = 0;

		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < c.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
				if (weekCount == 53) break;
				if (j == 0){
					monthCount += 1;
				}
				weekDays[dayCount] = j+1;
				dayCount += 1;
				Integer[] h = { weekCount, j+1};
				dayMonth.put(h, monthCount);
				System.out.println(h[0] +" " + h[1] + " " + dayMonth.get(h));

				if (dayCount == 7){
					dayCount = 0;
					weeks.add(weekDays);
					weekDays = new int[7];
					weekCount += 1;
				}		
			}
			month += 1;
			c.set(year, month, 1);
			
		}
		Integer[] x= {12, 19};
		System.out.println(12 +" " + 19 + " " + dayMonth.get(x));
		System.out.println("Made calendar");
		//		String[] months = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
		//		int mCount = -1;
		//		for (int i = 0; i < weeks.size(); i++) {
		//
		//			System.out.println("Week " +(i+1));
		//			for (int j = 0; j < 7; j++) {
		//				if(weeks.get(i)[j] == 1){
		//					mCount += 1;
		//					System.out.println(months[mCount]);
		//				}
		//				System.out.println(weeks.get(i)[j]);
		//			}
		//		}
		return new Year(weeks, dayMonth);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== left){
			weeknum -= 1;
			updateWeek();
		}
		else if(e.getSource()== right){
			weeknum += 1;
			updateWeek();
		}

	}


}

class Year{
	public ArrayList<int[]> weeks;
	public HashMap<Integer[], Integer> dayMonth;


	public Year(ArrayList<int[]> weeks, HashMap<Integer[], Integer> dayMonth){
		this.weeks = weeks;
		this.dayMonth = dayMonth;
	}


}