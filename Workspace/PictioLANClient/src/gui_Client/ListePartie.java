package gui_Client;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ListePartie extends JPanel
{
	private JLabel titre = new JLabel("Liste des parties");
	private JScrollPane msgScroll = new JScrollPane();
	private JTextArea affichage = new JTextArea(5,25);
	
	public ListePartie()
	{
		this.setLayout(new BorderLayout());
		this.add(titre,BorderLayout.NORTH);
		
		affichage.setFont(new Font("Courier",Font.PLAIN,12));
		affichage.setEditable(false);
		msgScroll.getViewport().add(affichage);
		this.add(msgScroll,BorderLayout.CENTER);
	}
	
	public void addMessage(String message)
	{
	    affichage.setText(message+"\n"+affichage.getText());
	    affichage.setCaretPosition(0);
	}
}
