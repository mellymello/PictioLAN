package gui;

import gui_Client.Information;
import gui_Client.ListePartie;
import gui_Client.Proposition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui_Client.InfoConnexion;
import configuration.Configuration;

public class Client extends JFrame implements Configuration {
	private Dessin dessin;
	private JPanel left = new JPanel();
	private JPanel pCentre = new JPanel();
	private Proposition proposition = new Proposition();
	private ListePartie listePartie = new ListePartie();
	private Information info = new Information();
	private InfoConnexion infoConnect = new InfoConnexion();

	private JPanel pCentreHaut = new JPanel();
	private JPanel pCentreHaut_buttons = new JPanel();
	private JPanel pCentreHaut_colors = new JPanel();
	private JButton btnClearEcran = new JButton("Effacer écran");
	private JButton btnPret = new JButton("Prêt");
	// private JButton btnSelectColor = new JButton("Couleurs");

	private BasePanel basePanel;
	private LogoPanel logoPanel;
	
	
	// colors chooser
	private JButton black;
	private JButton blue;
	private JButton brown;
	private JButton cyan;
	private JButton darkGreen;
	private JButton gray;
	private JButton green;
	private JButton magenta;
	private JButton orange;
	private JButton red;
	private JButton white;
	private JButton yellow;

	public Client(String titre) {
		super(titre);
		this.getContentPane().setLayout(new BorderLayout());

		this.setSize(LARGEUR_CLIENT, HAUTEUR_CLIENT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initializate();
		
		createLeft();
		createCenter();

//		this.getContentPane().add(left, BorderLayout.WEST);
//		this.getContentPane().add(pCentre, BorderLayout.CENTER);
//		this.getContentPane().add(proposition, BorderLayout.SOUTH);

		basePanel.add(left, BorderLayout.WEST);
		basePanel.add(pCentre, BorderLayout.CENTER);
		basePanel.add(proposition, BorderLayout.SOUTH);
		this.setVisible(true);
		
		this.getRootPane().setDefaultButton(proposition.getEnvoyer());
		this.setResizable(false);
	}

	private void initializate(){
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(500,400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("PictioLAN Client");
		this.setLayout(new BorderLayout());


		
		basePanel = new BasePanel();
		basePanel.setLayout(new BorderLayout());

		logoPanel = new LogoPanel();
		logoPanel.setPreferredSize(new Dimension(800,60));
		this.getContentPane().add(basePanel, BorderLayout.CENTER);
		this.getContentPane().add(logoPanel, BorderLayout.SOUTH);
		

	}
	
	private void createLeft() {
		left.setOpaque(false);
		left.setLayout(new GridLayout(3, 1));
		left.add(infoConnect);
		left.add(listePartie);
		left.add(info);
	}

	private void createCenter() {
		dessin = new Dessin(this);

		pCentreHaut.setLayout(new GridLayout(2, 1));
		pCentreHaut.setOpaque(false);
		pCentreHaut_buttons.setLayout(new GridLayout(1, 2));
		pCentreHaut_colors.setLayout(new GridLayout(2, 6));
		pCentreHaut_colors.setOpaque(false);
		
		pCentreHaut_buttons.add(btnClearEcran);
		pCentreHaut_buttons.add(btnPret);
		// pCentreHaut_buttons.add(btnSelectColor);

		pCentreHaut.add(pCentreHaut_buttons);
		
		colorButtons();
		pCentreHaut.add(pCentreHaut_colors);

		pCentre.setOpaque(false);
		pCentre.setLayout(new BorderLayout());
		pCentre.add(pCentreHaut, BorderLayout.NORTH);
		pCentre.add(dessin, BorderLayout.CENTER);
		btnClearEcran.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dessin.effacerDessin();

			}
		});

//		btnSelectColor.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Color trait = JColorChooser.showDialog(null,
//						"JColorChooser Sample", null);
//				dessin.setCurrentColor(trait);
//
//			}
//		});
	}

	private void loadColorsImages() {

		try {
			black = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/black.png"))));
			blue = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/blue.png"))));
			brown = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/brown.png"))));
			cyan = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/cyan.png"))));
			darkGreen = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/darkGreen.png"))));
			gray = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/gray.png"))));
			green = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/green.png"))));
			magenta = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/magenta.png"))));
			orange = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/orange.png"))));
			red = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/red.png"))));
			white = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/white.png"))));
			yellow = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/img/colors/yellow.png"))));
			
			
		} catch (IOException e) {
			// the image is not found...do a simple button

			black = new JButton("black");
			blue = new JButton("blue");
			brown= new JButton("brown");
			cyan = new JButton("cyan");
			darkGreen =new JButton("darkGreen");
			gray =new JButton("gray");
			green =new JButton("green");
			magenta =new JButton("magenta");
			orange = new JButton("orange");
			red = new JButton("red");
			white =new JButton("white");
			yellow =new JButton("yellow");
		}
	}
	
	private void colorButtons(){
		loadColorsImages();
		
		black.setBorder(BorderFactory.createEmptyBorder());
		black.setContentAreaFilled(false);
		blue.setBorder(BorderFactory.createEmptyBorder());
		blue.setContentAreaFilled(false);
		brown.setBorder(BorderFactory.createEmptyBorder());
		brown.setContentAreaFilled(false);
		cyan.setBorder(BorderFactory.createEmptyBorder());
		cyan.setContentAreaFilled(false);
		darkGreen.setBorder(BorderFactory.createEmptyBorder());
		darkGreen.setContentAreaFilled(false);
		gray.setBorder(BorderFactory.createEmptyBorder());
		gray.setContentAreaFilled(false);
		green.setBorder(BorderFactory.createEmptyBorder());
		green.setContentAreaFilled(false);
		magenta.setBorder(BorderFactory.createEmptyBorder());
		magenta.setContentAreaFilled(false);
		orange.setBorder(BorderFactory.createEmptyBorder());
		orange.setContentAreaFilled(false);
		red.setBorder(BorderFactory.createEmptyBorder());
		red.setContentAreaFilled(false);
		white.setBorder(BorderFactory.createEmptyBorder());
		white.setContentAreaFilled(false);
		yellow.setBorder(BorderFactory.createEmptyBorder());
		yellow.setContentAreaFilled(false);
		
		
		black.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dessin.setCurrentColor(Color.BLACK);
				
			}
		});


		blue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.BLUE);
				
			}
		});
		brown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(new Color(0xb12a01));
				
			}
		});
		cyan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.CYAN);
				
			}
		});
		darkGreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(new Color(0x286a0e));
				
			}
		});
		gray.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.GRAY);
				
			}
		});
		green.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.GREEN);
				
			}
		});
		magenta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.MAGENTA);
				
			}
		});
		orange.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.ORANGE);
				
			}
		});
		red.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.RED);
				
			}
		});
		white.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.WHITE);
				
			}
		});
		yellow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dessin.setCurrentColor(Color.YELLOW);
				
			}
		});
		
		
		pCentreHaut_colors.add(black);
		pCentreHaut_colors.add(blue);
		pCentreHaut_colors.add(brown);
		pCentreHaut_colors.add(cyan);
		pCentreHaut_colors.add(darkGreen);
		pCentreHaut_colors.add(gray);
		pCentreHaut_colors.add(white);
		pCentreHaut_colors.add(magenta);
		pCentreHaut_colors.add(orange);
		pCentreHaut_colors.add(red);
		pCentreHaut_colors.add(green);
		pCentreHaut_colors.add(yellow);
		
	}
	
	public void sendPoint(Point point) {
		// TODO Auto-generated method stub
		//METHODE POUR ENVOYER LES POINTS
	}
	
	class BasePanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/blueBackground.jpg")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}
	
	class LogoPanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/pictioLAN_logo.png")).getImage();
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}
}
