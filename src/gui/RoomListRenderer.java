package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import model.Room;

public class RoomListRenderer extends DefaultListCellRenderer{
	
	public Component getListCellRendererComponent(
			JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		int size = ((Room) value).getSize();
		int roomNumber = ((Room) value).getRoomNumber();
		
		label.setText("Rom nr "+roomNumber+" har plass til "+size+" personer");
		
		if (isSelected){
			label.setBackground(list.getSelectionBackground());
			label.setForeground(list.getSelectionForeground());
		}
		else{
			label.setBackground(list.getBackground());
			label.setForeground(list.getForeground());
		}
		label.setOpaque(true);
		
		return this;
	}

}
