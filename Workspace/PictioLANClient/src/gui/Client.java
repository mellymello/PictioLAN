package gui;

import gui_Client.Information;
import gui_Client.ListePartie;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Configuration.Configuration;



public class Client extends JFrame implements Configuration
{
	private Dessin dessin;
	private JPanel left = new JPanel();
	private JPanel pCentre = new JPanel();
	private ListePartie listePartie = new ListePartie();
	private Information info = new Information();
	
	public Client (String titre)
	{
		super(titre);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.setSize(LARGEUR_CLIENT,HAUTEUR_CLIENT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(pCentre, BorderLayout.CENTER);
	}
	private void creatLeft()
	{
		left.setLayout(new GridLayout(2,1));
		left.add(listePartie);
		left.add(info);
	}
}
