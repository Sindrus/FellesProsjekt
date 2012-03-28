package model;

import gui.ProjectPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;



public class Client {

	public static void main(String[] args){

		JFrame jf = new JFrame("Kalender");
		ProjectPanel pp = new ProjectPanel();
		jf.setLayout(new GridBagLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;

		jf.add(pp, g);
		jf.setVisible(true);
	};
}