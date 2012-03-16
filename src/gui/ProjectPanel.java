package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import database.*;

/**
 * 
 * Dette vil være selve driveren til guiet.
 * Her ligger mainmetoden.
 *
 */

public class ProjectPanel extends JPanel{
	
	LoginPanel login;
	Authenication auth;
	
	
	public ProjectPanel(){
		if (auth==null){
			login = new LoginPanel();
			add(login);
		}
		
	}
	
	public static void main(String[] args){
		
		JFrame jf = new JFrame("Kalender");
		ProjectPanel pp = new ProjectPanel();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(pp);
		jf.pack();
		jf.setSize(600, 400);
		jf.setVisible(true);
		
	}
	
}
