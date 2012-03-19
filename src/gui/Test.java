package gui;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class Test extends JFrame{

	MigLayout layout;
	
	public Test(){
		
		layout = new MigLayout("", "20[]20[]20", "20[]20[]20");
		add(new RoundButton("Test"));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Test();
	}
	
}
