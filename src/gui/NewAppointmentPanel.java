package gui;

/**
 * -*- coding:utf-8 -*-
 */

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.GUIListenerSupport;

public class NewAppointmentPanel extends JPanel {

	JButton cancel, complete, invite;
	JPanel whatPanel, whenPanel, desPanel, btnPanel;
	JTextField what;
	JTextArea description;
	JComboBox day, month, startTime, endTime;
	GUIListenerSupport gls;

	public NewAppointmentPanel() {
		String[] td = new String[24];
		for (int i = 0; i <= 23; i++) {
			if (i < 10)
				td[i] = "0" + i + ":00";
			else
				td[i] = i + ":00";
		}

		String[] mnd = { "Januar", "Februar", "Mars", "April", "Mai", "Juni",
				"Juli", "August", "September", "Oktober", "November",
				"Desember" };

		String[] dag = new String[32];
		for (int i = 0; i <= 31; i++) {
			dag[i] = Integer.toString(i);
		}

		gls = new GUIListenerSupport();
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.NORTHWEST;
		g.insets = new Insets(5, 5, 5, 5);

// whatPanel
		whatPanel = new JPanel();
		GridBagConstraints whatg = new GridBagConstraints();
		whatg.anchor = GridBagConstraints.WEST;

		whatg.gridy = 0;
		whatg.gridx = 0;
		JLabel h = new JLabel("Hva:");
		whatPanel.add(h, whatg);
		what = new JTextField("");
		Font f = new Font(what.getFont().getName(), what.getFont().getStyle(),
				14);
		what.setColumns(20);
		what.setFont(f);
		whatPanel.add(what, whatg);

// end whatPanel
		g.gridx = 0;
		g.gridy = 0;
		add(whatPanel, g);

// whenPanel
		whenPanel = new JPanel();
		GridBagConstraints wheng = new GridBagConstraints();

		whenPanel.add(new JLabel("Når:"), whatg);

		wheng.anchor = GridBagConstraints.WEST;
		wheng.gridx = 0;
		wheng.gridy = 0;
		whenPanel.add(new JLabel("Dag:"), wheng);

		wheng.gridx = 1;
		day = new JComboBox(dag);
		whenPanel.add(day, wheng);

		wheng.gridx = 2;
		whenPanel.add(new JLabel("Måned:"), wheng);

		wheng.gridx = 3;
		month = new JComboBox(mnd);
		whenPanel.add(month, wheng);

		wheng.gridx = 4;
		whenPanel.add(new JLabel("Fra:"), wheng);

		wheng.gridx = 5;
		startTime = new JComboBox(td);
		whenPanel.add(startTime, wheng);

		wheng.gridx = 6;
		whenPanel.add(new JLabel("Til:"), wheng);

		wheng.gridx = 7;
		endTime = new JComboBox(td);
		whenPanel.add(endTime, wheng);

// end whenPanel
		g.gridy = 1;
		add(whenPanel, g);

// desPanel
		desPanel = new JPanel();
		desPanel.setLayout(new GridBagLayout());
		GridBagConstraints desg = new GridBagConstraints();
		desg.anchor = GridBagConstraints.NORTHWEST;
		desg.gridx = 0;
		desg.gridy = 0;

		desPanel.add(new JLabel("Beskrivelse:"), desg);

		description = new JTextArea(4, 30);
		description.setFont(f);
		desg.gridx = 1;
		desPanel.add(description, desg);

// end desPanel
		g.gridy = 2;
		add(desPanel, g);

// btnPanel
		btnPanel = new JPanel();
		btnPanel.setLayout(new GridBagLayout());
		GridBagConstraints btng = new GridBagConstraints();
		btng.anchor = GridBagConstraints.CENTER;
		btng.gridx = 0;
		btng.gridy = 0;
		btng.insets = new Insets(0, 30, 0, 30);

		complete = new JButton("Fullført");
		complete.addActionListener(new Decline());
		btnPanel.add(complete, btng);

		btng.insets.left = 5;
		btng.gridx = 1;
		cancel = new JButton("Avbryt");
		cancel.addActionListener(new Accept());
		btnPanel.add(cancel, btng);

		btng.gridx = 2;
		btng.insets.right = 0;
		invite = new JButton("Inviter til møte");
		invite.addActionListener(new Decline());
		btnPanel.add(invite, btng);
		
// end btnPanel
		g.anchor = GridBagConstraints.CENTER;
		g.gridy = 3;
		add(btnPanel, g);
	}

	class Accept implements ActionListener { // no logic.
		public void actionPerformed(ActionEvent e) {
		}
	}

	class Decline implements ActionListener { // no logic.
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
