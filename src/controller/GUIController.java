package controller;

import gui.ProjectPanel;

import java.awt.Color;
import java.awt.Dimension;
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
		jf.getContentPane().setPreferredSize(new Dimension(jf.getWidth(), jf.getHeight()));
		jf.setContentPane(pp);
		
		jf.setBackground(Color.BLACK);
		jf.getContentPane().setBackground(Color.MAGENTA);
		jf.setVisible(true);
	}
	
}
