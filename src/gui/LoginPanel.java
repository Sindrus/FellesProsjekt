package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * Her lages guiet til loginskjermen. 
 *
 */
public class LoginPanel extends JPanel{
	
	JTextField usernameField;
	JPasswordField passwordField;
	
	public LoginPanel(){
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		
		
		g.anchor = GridBagConstraints.WEST;
		g.weightx=0.5;
		g.weighty=0.5;
		g.gridx=0;
		g.gridy=0;
		g.insets = new Insets(2, 5, 0, 5);
		
		g.gridwidth=2;
		add(new JLabel("Her legger vi inn en logo"),g);
		
		g.gridwidth=1;
		g.gridy=1;
		add(new JLabel("Login navn"),g);
		
		usernameField = new JTextField("",15);
		
		g.gridx=1;
		g.gridy=1;
		add(usernameField,g);
		
		g.gridx=0;
		g.gridy=2;
		add(new JLabel("Passord"),g);
		
		
		passwordField = new JPasswordField("",15);
		
		g.gridx=1;
		g.gridy=2;
		add(passwordField,g);
			
	}
}
