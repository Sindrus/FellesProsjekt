package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import model.DBUser;
import model.User;
import util.GUIListenerSupport;

/**
 * 
 * @author Even
 *	Panelet som inneholder ubesvarte m�teinnkallinger
 *
 */

public class InvitationListPanel extends JPanel {

	private JPanel inkallingPanel;
	public JList inkallingList;
	private DefaultListModel defaultPersonListModel;

	public InvitationListPanel(){

		GUIListenerSupport gls = new GUIListenerSupport();
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.CENTER;
		g.insets = new Insets(5, 5, 5, 5);
		g.weightx = 0.5;
		g.weighty = 0.5;
		
		GridBagConstraints invg = new GridBagConstraints();
		invg.anchor = GridBagConstraints.NORTH;
		invg.insets = new Insets(5, 5, 5, 5);
		invg.gridx=0;
		invg.gridy=0;
		
		inkallingPanel = new JPanel(new GridBagLayout());
	
		JLabel minkall = new JLabel("Møteinnkallinger");
		Font f = new Font(minkall.getFont().getName(), minkall.getFont().getStyle(),
				16);
		minkall.setFont(f);
		inkallingPanel.add(minkall,invg);
		defaultPersonListModel = new DefaultListModel();
//		defaultPersonListModel.addElement(DBUser.getUserAppointments((int)User.getId()));
		inkallingList = new JList(defaultPersonListModel);
		inkallingList.setVisibleRowCount(5);
		inkallingList.setFixedCellWidth(140);
		inkallingList.setFixedCellHeight(40);
		inkallingList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		invg.gridy=1;
		inkallingPanel.add(inkallingList,invg);
		
		JScrollPane personScroll = new JScrollPane(inkallingList);
		personScroll.setMinimumSize(new Dimension(100, 300));
		personScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inkallingPanel.add(personScroll,invg);
		
		g.gridy=0;
		add(inkallingPanel,g);
		
	}
	
//	public static void main(String args[]) {
//		JFrame frame = new JFrame();
//		frame.add(new InvitationListPanel());
//		frame.pack();
//		frame.setVisible(true);
//	}
}
