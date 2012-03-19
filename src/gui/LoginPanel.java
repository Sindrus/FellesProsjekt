package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

/**
 * 
 * Her lages guiet til loginskjermen. 
 *
 */
public class LoginPanel extends JPanel implements ActionListener{
	
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginBtn;
	
	PropertyChangeSupport pcs;
	
	public LoginPanel(){
		pcs = new PropertyChangeSupport(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.CENTER;
		g.weightx=0.5;
		g.weighty=0.5;
		g.gridx=0;
		g.gridy=0;
		g.insets = new Insets(10, 5, 0, 5);
		g.insets.bottom = 40;
	
		g.gridwidth=2;
		add(new JLabel("Her legger vi inn en logo"),g);
		
		g.anchor = GridBagConstraints.WEST;
		g.insets.bottom = 5;
		g.gridwidth=1;
		g.gridy=1;
		add(new JLabel("Brukernavn:"),g);
		
		usernameField = new JTextField("",15);
		
		g.gridx=1;
		g.gridy=1;
		add(usernameField,g);
		
		g.gridx=0;
		g.gridy=2;
		add(new JLabel("Passord:"),g);
		
		passwordField = new JPasswordField("",15);
		
		g.gridx=1;
		g.gridy=2;
		add(passwordField,g);
		
		loginBtn = new JButton("Logg inn");
		loginBtn.addActionListener(this);
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridwidth=2;
		g.gridx=0;
		g.gridy=3;
		add(loginBtn,g);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginBtn){
			pcs.firePropertyChange("LoginBtnPushed", usernameField.getText(), passwordField.getPassword());
			System.out.println("Passord"+passwordField.getPassword().toString());
		}
	}
}
