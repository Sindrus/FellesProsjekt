package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewAppointmentPanel extends JPanel {
	
	JButton Avbryt, Full, Invite;
	JTextField Hva,Beskrivelse;
	JComboBox Dag, Maned, Stid, Etid;
	PropertyChangeSupport pcs;

	public NewAppointmentPanel(){

		String[] td = new String[24];
		for (int i = 0; i<=23; i++)
		{
		if (i<10)
			td[i] = "0" + i + ":00";
		else
			td[i] = i + ":00";
		}
		
		String[] mnd = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
		
		String[] dag = new String[32];
		for (int i = 0; i<=31; i++)
		{
		dag[i] = Integer.toString(i);
		}
		
		pcs = new PropertyChangeSupport(this);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=0;
		JLabel h = new JLabel("Hva:");
		add(h,g);
		Hva = new JTextField(""); 
		Font f = new Font(Hva.getFont().getName(),Hva.getFont().getStyle(),14);
		Hva.setColumns(20);
		Hva.setFont(f);
		add(Hva,g);

		g.anchor = GridBagConstraints.WEST;
		g.gridy = 1;
		JLabel n = new JLabel("Når:");
		add(n,g);
		
		JLabel d = new JLabel("Dag:");
		add(d,g);
		Dag = new JComboBox(dag);
		add(Dag,g);
		
		JLabel m = new JLabel("Måned:");
		add(m,g);
		Maned = new JComboBox(mnd);
		add(Maned,g);
		
		JLabel st = new JLabel("Fra:");
		add(st,g);
		Stid = new JComboBox(td);
		add(Stid,g);
		
		JLabel et = new JLabel("Til:");
		add(et,g);
		Etid = new JComboBox(td);
		add(Etid,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=2;
		JLabel b = new JLabel("Beskrivelse:");
		add(b,g);
		Beskrivelse = new JTextField("");
		Beskrivelse.setFont(f);
		Beskrivelse.setColumns(50);
		add(Beskrivelse,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=3;
		Avbryt = new JButton("Avbryt");
		Avbryt.addActionListener(new Godta());
		add(Avbryt,g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridy=3;
		Full = new JButton("Fullført");
		Full.addActionListener(new Avvis());
		add(Full,g);
		
		g.anchor = GridBagConstraints.EAST;
		g.gridy=3;
		Invite = new JButton("Inviter til møte");
		Invite.addActionListener(new Avvis());
		add(Invite,g);
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
		frame.add(new NewAppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
