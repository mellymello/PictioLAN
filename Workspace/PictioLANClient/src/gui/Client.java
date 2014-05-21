package gui;

import gui_Client.Information;
import gui_Client.ListePartie;
import gui_Client.Proposition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui_Client.InfoConnexion;
import configuration.Configuration;



public class Client extends JFrame implements Configuration
{
	private Dessin dessin;
	private JPanel left = new JPanel();
	private JPanel pCentre = new JPanel();
	private Proposition proposition = new Proposition();
	private ListePartie listePartie = new ListePartie();
	private Information info = new Information();
	private InfoConnexion infoConnect = new InfoConnexion();
	
	private JPanel pCentreHaut = new JPanel();
	private JButton btnClearEcran = new JButton("Effacer écran");
	private JButton btnPret = new JButton("Prêt");
	private JButton btnSelectColor = new JButton("Couleurs");
	
	public Client (String titre)
	{
		super(titre);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.setSize(LARGEUR_CLIENT,HAUTEUR_CLIENT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createLeft();
		createCenter();
		
		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(pCentre, BorderLayout.CENTER);
		this.getContentPane().add(proposition, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	private void createLeft()
	{
		left.setLayout(new GridLayout(3,1));
		left.add(infoConnect);
		left.add(listePartie);
		left.add(info);
	}
	
	private void createCenter()
	{
		dessin = new Dessin(this);
		
		pCentreHaut.setLayout(new GridLayout(1,2));
		pCentreHaut.add(btnClearEcran);
		pCentreHaut.add(btnPret);
		pCentreHaut.add(btnSelectColor);
		
		pCentre.setLayout(new BorderLayout());
		pCentre.add(pCentreHaut,BorderLayout.NORTH);
		pCentre.add(dessin,BorderLayout.CENTER);
		btnClearEcran.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dessin.effacerDessin();
				
			}
		});
		
		btnSelectColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color trait = JColorChooser.showDialog(null,
                        "JColorChooser Sample", null);
				dessin.setColor(trait);
				
			}
		});
	}
	public void sendPoint(Rectangle rectangle) {
		// TODO Auto-generated method stub
		
	}
}
