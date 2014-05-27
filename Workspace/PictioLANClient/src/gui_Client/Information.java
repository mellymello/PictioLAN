package gui_Client;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Information extends JPanel
{

	private JLabel titre = new JLabel("     Pseudo                            | Eq | Pret");
	private JScrollPane msgScroll = new JScrollPane();
	private JTextArea affichage = new JTextArea(5,25);
	
	public Information()
	{
		this.setLayout(new BorderLayout());
		this.add(titre,BorderLayout.NORTH);
		
		affichage.setFont(new Font("Courier",Font.PLAIN,12));
		affichage.setEditable(false);
		msgScroll.getViewport().add(affichage);
		this.add(msgScroll,BorderLayout.CENTER);
	}
	
	public void setMessage(String message)
	{
		affichage.setText(message);
	}
}
