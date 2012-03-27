package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import util.ChangeType;
import util.DateHelpers;
import util.GUIListener;
import util.GUIListenerSupport;


import model.Appointment;

/**
 * 
 * @author Jorgen
 * 
 * Panelet som inneholder den ukentlige kalendern.
 * 
 * Static variables:
 * c = Calendar.instance er kalender na.
 * weeknum = ukenummer na
 * daynum = dag (i manad) na.
 * monthnum = manadnummer (i ar).
 * 
 * Alle andre er et (fail) logikk for a sette in korrekt dato i uka.
 */

public class WeeklyCalendarPanel extends JPanel implements ActionListener{

	private static Calendar c = Calendar.getInstance();
	private static String[] weekdays = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lordag","Sondag"};
	private static String[] monthnames = {"Jan", "Feb", "Mars", "April", "Mai", "Juni", "Juli", "Aug", "Sep", "Okt", "Nov", "Des"};
	private static int weeknum = c.get(Calendar.WEEK_OF_YEAR);
	private JButton left,right;
	private JLabel[] dayLabels = new JLabel[7];
	private DayListPanel[] dayList = new DayListPanel[7];
	private GridBagConstraints g = new GridBagConstraints();
	private JLabel ukenummer = new JLabel();
	private Year y;
	private JScrollPane[] dayScroll;
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private long firstDay, lastDay;
	private GUIListenerSupport gls;


	public WeeklyCalendarPanel(){

		gls = new GUIListenerSupport();
		y = getYear(Calendar.getInstance().get(Calendar.YEAR));
		dayScroll = new JScrollPane[7];

		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		g.gridy = 0;
		g.gridx = 0;
		left = new JButton("<=");
		left.addActionListener(this);
		add(left,g);

		g.gridx = 6;
		right = new JButton("=>");
		right.addActionListener(this);
		add(right,g);

		g.gridx = 3;
		ukenummer = new JLabel("Ukenummer: " + Integer.toString(weeknum) + "            ");
		add (ukenummer,g);

		g.gridy = 3;
		g.gridx = 0;
		for (int i = 0; i < 7; i++) {
			dayLabels[i] = new JLabel(String.valueOf(i));
			add(dayLabels[i], g);
			g.gridx += 1;
		}

		g.gridy = 4;
		g.gridx = 0;
		for (int i = 0; i < 7; i++) {

			dayList[i] = new DayListPanel();
			//dayList[i].setPreferredSize(new Dimension(415,420));

			dayList[i].setBorder(BorderFactory.createEtchedBorder());
			dayScroll[i] = new JScrollPane(dayList[i],JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			//dayScroll[i].setPreferredSize(new Dimension(415,420));
			//dayScroll[i].setViewportView(dayList[i],JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			dayScroll[i].setVisible(true);
			dayScroll[i].setMinimumSize(new Dimension((int)tool.getScreenSize().getWidth()/13, (int)(tool.getScreenSize().getHeight()/(1.5))));
			add(dayScroll[i], g);
			g.gridx += 1;

		}



		updateWeek();


		validate();
		repaint();
		revalidate();

	}


	public void addGuiListener(GUIListener l){
		gls.add(l);
	}

	private void updateWeek(){
		System.out.println("updating week " + weeknum);
		for (int i = 0; i < weekdays.length; i++) {
			String dm= String.valueOf(weeknum) + String.valueOf(y.weeks.get(weeknum)[i]);
			dayLabels[i].setText("   " + weekdays[i] + " " + String.valueOf(y.weeks.get(weeknum)[i]) + ". " + monthnames[y.dayMonth.get(dm)]);
		}

		firstDay = DateHelpers.convertToTimestamp(y.year, (int)(y.dayMonth.get(String.valueOf(weeknum) + String.valueOf(y.weeks.get(weeknum)[0]))), (y.weeks.get(weeknum)[0]), 0, 0, 0);
		lastDay = DateHelpers.convertToTimestamp(y.year, (int)(y.dayMonth.get(String.valueOf(weeknum) + String.valueOf(y.weeks.get(weeknum)[6]))), (y.weeks.get(weeknum)[6]), 0, 0, 0);



		ukenummer.setText("Ukenummer: " + Integer.toString(weeknum));

	}
	//
	//	public static void main(String args[]) {
	//		JFrame frame = new JFrame();
	//		frame.add(new WeeklyCalendarPanel());
	//		frame.setSize(1000, 700);
	//		//frame.pack();
	//		frame.setVisible(true);
	//
	//	}


	public long getLastDay(){
		return lastDay;
	}
	public long getFirstDay(){
		return firstDay;
	}

	private Year getYear(int year){

		int month = 0;
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);



		ArrayList<int[]> weeks = new ArrayList<int[]>();

		int[] weekDays = new int[7];
		int dayCount = 6;
		int monthCount = -1;
		HashMap<String, Integer > dayMonth = new HashMap<String, Integer>();
		int weekCount = 0;
		ArrayList<String> keys =  new ArrayList<String>();

		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < c.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
				if (weekCount == 53) break;
				if (j == 0){
					monthCount += 1;
				}
				weekDays[dayCount] = j+1;
				dayCount += 1;
				String h = String.valueOf(weekCount) + String.valueOf(j+1);
				dayMonth.put(h, monthCount);

				keys.add(h);
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
		return new Year(weeks, dayMonth, keys);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== left){
			weeknum -= 1;
			gls.notifyListeners(ChangeType.PREVWEEK, null);
		}
		else if(e.getSource()== right){
			weeknum += 1;
			gls.notifyListeners(ChangeType.NEXTWEEK, null);
		}
		else{
			ArrayList<Object> a = new ArrayList<Object>();
			a.add(e.getSource());
			gls.notifyListeners(ChangeType.APPBUTTON, a);
		}

	}


	public void setAppointments(ArrayList<Appointment> a) {

		updateWeek();
		System.out.println("Setting " + a.size() + " appointments");

		for (int i = 0; i < dayList.length; i++) {

			dayList[i].clearList();


			for (int j = 0; j < a.size(); j++) {

				if(DateHelpers.convertFromTimestamp(a.get(j).getStart()).get("day") == (y.weeks.get(weeknum)[i])){
					Appointment app = a.get(j);
					AButton b = new AButton(a.get(j));
					b.addActionListener(this);
					dayList[i].addButton(b);
					
				}
			}


		}


	}




}

/**
 * @deprecated
 * @author jorgen
 *
 */
class ButtonCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(final JList list,
			final Object value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		return new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.setColor(isSelected ? list.getSelectionBackground() : list
						.getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(isSelected ? list.getSelectionForeground() : list
						.getForeground());
				String text = ((JButton) value).getText();
				g.drawString(text, 0, 0);
			}



		};
	}
}



class Year{
	public int year = 2012;
	public ArrayList<int[]> weeks;
	public HashMap<String, Integer> dayMonth;
	public ArrayList<String> keys;


	public Year(ArrayList<int[]> weeks, HashMap<String, Integer> dayMonth, ArrayList<String> keys){
		this.weeks = weeks;
		this.dayMonth = dayMonth;
		this.keys = keys;
	}




}
