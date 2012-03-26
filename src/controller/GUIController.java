package controller;

import gui.ProjectPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


/**
 * This class controls the GUI, and takes input from changes in the Model. 
 * @author jorgen
 *
 */
public class GUIController {

	private ProjectPanel pp;
	
	public GUIController(){
		JFrame jf = new JFrame("Kalender");
		pp = new ProjectPanel();
		jf.setLayout(new GridBagLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;

		jf.add(pp, g);
		jf.pack();
		jf.setVisible(true);
	}
	
}
