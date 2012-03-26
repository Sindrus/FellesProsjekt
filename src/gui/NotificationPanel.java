package gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * 
 * @author sindre
 * 
 * Panel som inneholder en liste over alle møteinvitasjoner
 * 
 */

public class NotificationPanel extends JPanel{
	JList list = new JList();
	JButton close = new JButton();
	
	NotificationPanel(){
		
		GridBagConstraints g = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		g.gridx = 0;
		g.gridy = 0;
		list = new JList();
		add(list,g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridy = 1;
		close = new JButton("Lukk");
		add(close,g);
		close.addActionListener(new Close());
	}

	class Close implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			close.
	}
	public static void main(String args[]) {
		JFrame frame = new JFrame("Appointment");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new NotificationPanel());
		frame.pack();
		frame.setVisible(true);
	}
}