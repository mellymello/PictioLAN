package gui;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JStatistic extends JPanel{
	

	private JList<String> activesGamers;
	private JScrollPane  gamerListPanel;
	
	public JStatistic ()
	{
		this.setLayout(new BorderLayout());
	}

}
