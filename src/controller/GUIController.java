package controller;

import gui.ProjectPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;


/**
 * This class controls the GUI, and takes input from changes in the Model. 
 * @author jorgen
 *
 */
public class GUIController {

	private ProjectPanel pp;
	private Toolkit tool = Toolkit.getDefaultToolkit();
	
	public GUIController(){
		JFrame jf = new JFrame("Kalender");
		pp = new ProjectPanel();
		jf.setLayout(new GridBagLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;
		jf.setContentPane(pp);
		jf.getContentPane().setPreferredSize(new Dimension((int)tool.getScreenSize().getWidth(), (int)(tool.getScreenSize().getHeight())));
		
		jf.setBackground(Color.BLACK);
		jf.getContentPane().setBackground(Color.MAGENTA);
		jf.setVisible(true);
	}
	
}
