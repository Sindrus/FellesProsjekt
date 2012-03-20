package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DeleteAppointmentDialog extends JPanel {
	
	JButton A; //test
	public DeleteAppointmentDialog(){
		A = new JButton("Test");
		A.addActionListener(new Test());
		add(A);
	}
	
	class Test implements ActionListener{
	public void actionPerformed(ActionEvent e) { //COPY HERE
		Object[] options = {"Nei", "Ja"};
		Object message = "Er du sikker pa at du vil slette avtale?";
		JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(A),message, "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null , options, options[0]);
		} // FINISH COPY
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.add(new DeleteAppointmentDialog());
		frame.pack();
		frame.setVisible(true);
	}
}