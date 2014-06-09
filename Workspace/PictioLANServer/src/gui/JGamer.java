package gui;

import gamer.ManageGamer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JGamer extends JPanel {

	// private ManageGamer manager;

	private JList<String> activesGamers;
	private JScrollPane  gamerListPanel;

	private JPanel buttonPanel;
	
	private JButton deleteButton;
	private JButton refreshButton;
	
	public JGamer() {
		// manager = g;

		this.setLayout(new BorderLayout());
		activesGamers = new JList<String>();

		setGamerList();
		
		gamerListPanel = new JScrollPane(activesGamers);
		
		buttonPanel = new JPanel(new FlowLayout());
		deleteButton=new JButton("Delete Player");
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((activesGamers.getSelectedValue()) != null) {
					ManageGamer.deleteGamer(activesGamers.getSelectedValue());
					setGamerList();
				} else {
					JOptionPane.showMessageDialog(null,
							"You have to select a gamer !", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

				
			}
		});
		refreshButton = new JButton("Refresh Gamer");
		refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setGamerList();
				
			}
		});
		buttonPanel.add(deleteButton);
		buttonPanel.add(refreshButton);



		this.add(gamerListPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setGamerList() {
		String[] tmp = ManageGamer.getGamers().toArray(new String[0]);
		activesGamers.setListData(tmp);
	}

}
