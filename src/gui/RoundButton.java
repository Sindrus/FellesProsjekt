package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class RoundButton extends JButton {

	public RoundButton(String text){
		
		setText(text);
		
	}
	
	@Override
	protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING
        		, RenderingHints.VALUE_ANTIALIAS_ON));
        Shape first = g.getClip();
        
        RoundRectangle2D rect = new RoundRectangle2D.Double();
        double arc = Math.ceil(getSize().getHeight()/3);
        rect.setRoundRect(0, 0, Math.ceil(getSize().getWidth()), 
        		Math.ceil(getSize().getHeight()), arc, arc);

        Area second = new Area(getBounds());
        second.subtract(new Area(rect));
        Area finalClip = new Area(first);
        finalClip.subtract(second);
        g2.setClip(finalClip);
        super.paintComponent(g2);
    }
	
}
