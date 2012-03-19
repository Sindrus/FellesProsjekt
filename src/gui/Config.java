package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Config {

	public static Color GREEN = new Color(154, 245, 175);
	
	
	/**
	 * @param 	JFrame frame
	 * @return 	the point placing a JFrame at the exact center of the screen
	 */
	public static Point getMidScreen(JFrame frame){
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = tk.getScreenSize().width;
		int height = tk.getScreenSize().height;
		return new Point((int)(width/2) - (int)(frame.getPreferredSize().width/2),
				(int)(height/2) - (int)(frame.getPreferredSize().height/2));
		
	}
	
}
