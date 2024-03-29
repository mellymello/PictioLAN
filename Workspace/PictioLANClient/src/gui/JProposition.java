package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import connection.PictioLan;

//import connection.ConnectionServer;

public class JProposition extends JPanel
{
	private boolean actif = true;
	private JPanel proposition = new JPanel();
	private JLabel titre = new JLabel("Proposition");
	private JTextField txtProp = new JTextField(25);
	private JButton envoyer = new JButton("Envoyer");
	
	private JScrollPane msgScroll = new JScrollPane();
	private JTextArea affichage = new JTextArea(5,25);
	
	public JProposition()
	{
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		proposition.setLayout(new FlowLayout());
		proposition.setOpaque(false);
		
		titre.setForeground(Color.WHITE);
		proposition.add(titre);
		proposition.add(txtProp);
		proposition.add(envoyer);
		
		affichage.setFont(new Font("Courier",Font.PLAIN,12));
		affichage.setEditable(false);
		msgScroll.getViewport().add(affichage);
		
		this.add(proposition, BorderLayout.NORTH);
		this.add(msgScroll, BorderLayout.CENTER);
		
		envoyer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
					
					System.out.println("Vue1 = " + txtProp.getText());
					
					if(PictioLan.modele_gamer != null && PictioLan.modele_gamer.getChat() != null && PictioLan.modele_gamer.getChat().isConnect())
						PictioLan.modele_gamer.getChat().addMessageToBuffer(txtProp.getText());				

				
				addText(txtProp);
			}
		});
		envoyer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
			        
					if(PictioLan.modele_gamer.getChat() != null && PictioLan.modele_gamer.getChat().isConnect())
						PictioLan.modele_gamer.getChat().addMessageToBuffer(txtProp.getText());

					
					addText(txtProp);
			    }
				
			}
		});
    
	}
	
	public void setActif(boolean actif)
	{
		this.actif = actif;
		txtProp.setEnabled(actif);
		envoyer.setEnabled(actif);
	}
	
	public void setEnabled(boolean isEnabled)
	{
		txtProp.setEnabled(isEnabled);
		envoyer.setEnabled(isEnabled);
	}
	
	public void addText(JTextField message)
	{
		affichage.setText(affichage.getText()+ message.getText()+"\n");
		txtProp.setText("");
	}
	
	public void addText(String message)
	{
		affichage.setText(affichage.getText()+ message+"\n");
		txtProp.setText("");
	}
	
	public String getProposition()
	{
		return txtProp.getText();
	}
	public JButton getEnvoyer ()
	{
		return envoyer;
	}
}