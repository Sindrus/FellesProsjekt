package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Even
 *	Startvinduet som inneholder selve kalenderen, varslinger, samt liste over 
 *	møteinnkallinger og andres kalendere
 *
 */

public class CalendarDashboard extends JPanel {

	private MigLayout layout;
	
	public CalendarDashboard(){
		
		layout = new MigLayout("debug", "50[]10[]50", "50[]10[]10[]50");
		
		
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test");
		frame.add(new CalendarDashboard());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
