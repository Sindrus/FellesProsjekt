package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GodtaAvvisPanel extends JPanel {
	
	JButton Godta, Avvis;
	JTextField Hva,N�r,Beskrivelse;
	PropertyChangeSupport pcs;
	
	public GodtaAvvisPanel(){
		
		pcs = new PropertyChangeSupport(this);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=0;
		Hva = new JTextField("Hva:" ); //+ Read in the meeting name.
		Font f = new Font(Hva.getFont().getName(),Hva.getFont().getStyle(),14);
		Hva.setBorder(null);
		Hva.setFont(f);
		Hva.setColumns(25);
		Hva.setEditable(false);
		add(Hva,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=1;
		N�r = new JTextField("N�r:" ); //+ Read in the meeting time.
		N�r.setBorder(null);
		N�r.setFont(f);
		N�r.setColumns(25);
		N�r.setEditable(false);
		add(N�r,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=2;
		Beskrivelse = new JTextField("Beskrivelse:" ); //+ Read in the meeting description.
		Beskrivelse.setBorder(null);
		Beskrivelse.setFont(f);
		Beskrivelse.setEditable(false);
		add(Beskrivelse,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=3;
		g.gridx=0;
		Godta = new JButton("Godta");
		Godta.addActionListener(new Godta());
		add(Godta,g);
		
		g.anchor = GridBagConstraints.EAST;
		g.gridy=3;
		g.gridx=0;
		Avvis = new JButton("Avvis");
		Avvis.addActionListener(new Avvis());
		add(Avvis,g);
	}
	
	
	class Godta implements ActionListener { //no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class Avvis implements ActionListener { //no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new GodtaAvvisPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
