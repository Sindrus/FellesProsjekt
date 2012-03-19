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

public class SlettAvtalePanel extends JPanel {

	JButton NeiBt, JaBt;
	JTextField Msg;
	PropertyChangeSupport pcs;
	
	public SlettAvtalePanel(){
		pcs = new PropertyChangeSupport(this);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridx=0;
		g.gridy=0;
		Msg = new JTextField("Er du sikkert pa at du vil slette avtale?");
		Msg.setHorizontalAlignment(JTextField.CENTER);
		Font f = new Font(Msg.getFont().getName(),Msg.getFont().getStyle(),18);
		Msg.setBorder(null);
		Msg.setFont(f);
		Msg.setColumns(25);
		Msg.setEditable(false);
		add(Msg,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridy=1;
		g.gridx=0;
		NeiBt = new JButton("Nei");
		NeiBt.addActionListener(new NeiBt());
		add(NeiBt,g);
		
		g.anchor = GridBagConstraints.EAST;
		g.gridy=1;
		g.gridx=0;
		JaBt = new JButton("Ja");
		JaBt.addActionListener(new JaBt());
		add(JaBt,g);
	}
	
	
	class NeiBt implements ActionListener { //no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class JaBt implements ActionListener { //no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new SlettAvtalePanel());
		frame.pack();
		frame.setVisible(true);
	}
}
