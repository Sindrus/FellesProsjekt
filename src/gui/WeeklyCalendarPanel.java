package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

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


	public WeeklyCalendarPanel(){
		y = getYear(Calendar.getInstance().get(Calendar.YEAR));

		setLayout(new GridBagLayout());
		g.gridy = 0;
		g.gridx = 0;
		left = new JButton("<=");
		left.addActionListener(this);
		add(left,g);

		g.gridx = 1;
		right = new JButton("=>");
		right.addActionListener(this);
		add(right,g);

		g.gridx = 2;
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
			//dayScroll[i] = new JScrollPane(dayList[i]);
			add(dayList[i], g);
			g.gridx += 1;

		}



		updateWeek();


		//validate();
		//repaint();
		//revalidate();

	}


	private void updateWeek(){

		for (int i = 0; i < weekdays.length; i++) {
			String dm= String.valueOf(weeknum) + String.valueOf(y.weeks.get(weeknum)[i]);
			dayLabels[i].setText("   " + weekdays[i] + " " + String.valueOf(y.weeks.get(weeknum)[i]) + ". " + monthnames[y.dayMonth.get(dm)]);
		}


		//get all appointments for this week

		for (int i = 0; i < dayList.length; i++) {
			
			dayList[i].clearList();
			for (int j = 0; j < 10; j++) {
				
				JButton b = new JButton();
				b.setLayout(new BorderLayout());
				JLabel label1 = new JLabel("Appointment " + String.valueOf(j));
				JLabel label2 = new JLabel("From 15:00");
				JLabel label3 = new JLabel("To 16:40");
				b.add(BorderLayout.NORTH,label1);
				b.add(BorderLayout.CENTER, label3);
				b.add(BorderLayout.SOUTH,label2);
				dayList[i].addButton(b);
			}
	

		}


		ukenummer.setText("Ukenummer: " + Integer.toString(weeknum));

	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new WeeklyCalendarPanel());
		frame.setSize(1000, 700);
		//frame.pack();
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
			updateWeek();
		}
		else if(e.getSource()== right){
			weeknum += 1;
			updateWeek();
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
	public ArrayList<int[]> weeks;
	public HashMap<String, Integer> dayMonth;
	public ArrayList<String> keys;


	public Year(ArrayList<int[]> weeks, HashMap<String, Integer> dayMonth, ArrayList<String> keys){
		this.weeks = weeks;
		this.dayMonth = dayMonth;
		this.keys = keys;
	}




}
