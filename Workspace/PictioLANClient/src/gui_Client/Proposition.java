package gui_Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Proposition extends JPanel
{
	
	private boolean actif = true;
	private JPanel proposition = new JPanel();
	private JLabel titre = new JLabel("Proposition");
	private JTextField txtProp = new JTextField(25);
	private JButton envoyer = new JButton("Envoyer");
	
	private JScrollPane msgScroll = new JScrollPane();
	private JTextArea affichage = new JTextArea(5,25);
	
	public Proposition()
	{
		this.setLayout(new BorderLayout());
		proposition.setLayout(new FlowLayout());
		
		proposition.add(titre);
		proposition.add(txtProp);
		proposition.add(envoyer);
		
		affichage.setFont(new Font("Courier",Font.PLAIN,12));
		affichage.setEditable(false);
		msgScroll.getViewport().add(affichage);
		
		this.add(proposition, BorderLayout.NORTH);
		this.add(msgScroll, BorderLayout.CENTER);
    
	}
	
	public void setActif(boolean actif)
	{
		this.actif = actif;
		txtProp.setEnabled(actif);
		envoyer.setEnabled(actif);
	}
	
	public void addMessage(String message)
	{
    //affichage a l'envers
		affichage.setText(message+"\n" + affichage.getText());
	}
	
	public void setEnabled(boolean isEnabled)
	{
		txtProp.setEnabled(isEnabled);
		envoyer.setEnabled(isEnabled);
	}
	
	public JButton getEnvoyer()
	{
		return envoyer;
	}
	
	public String getProposition()
	{
		return txtProp.getText();
	}
  
  public JTextField getJProposition()
  {
    return txtProp;
  }
	
	public void viderText()
	{
		txtProp.setText("");
	}
  
  public void setProp(String prop)
  {
    txtProp.setText(prop);
  }
  
}