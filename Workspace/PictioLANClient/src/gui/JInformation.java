package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JInformation extends JPanel
{
	private JLabel titre = new JLabel("Timer");
	private JScrollPane msgScroll = new JScrollPane();
	private JTextArea affichage = new JTextArea(5,25);
	
	public JInformation()
	{
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		titre.setForeground(Color.WHITE);
		this.add(titre,BorderLayout.NORTH);
		
		affichage.setFont(new Font("Courier",Font.PLAIN,12));
		affichage.setEditable(false);
		msgScroll.getViewport().add(affichage);
		this.add(msgScroll,BorderLayout.CENTER);
	}
	
	public void setMessage(String message)
	{
		affichage.setText(message);
		repaint();
	}
}
