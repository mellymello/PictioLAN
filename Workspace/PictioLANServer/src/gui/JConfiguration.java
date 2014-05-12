package gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import server.*;

public class JConfiguration extends JPanel{
	
	private Server config;
	
	private JPanel grid = new JPanel();
	
	private JButton buttonStart;
	private JButton buttonStop;
	private JLabel info;
	
	public JConfiguration(Server c) {
		config = c;
		
		grid.setLayout(new BorderLayout());
		
		buttonStart = new JButton("Start");
		buttonStop = new JButton("Stop");

		grid.add(buttonStart,BorderLayout.EAST);
		grid.add(buttonStop,BorderLayout.WEST);
		
		add(grid,BorderLayout.CENTER);
	}
	

}
