package gui;

import game.Gamer;

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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import connection.PictioLan;


public class JClient extends JFrame implements Configuration {
	private JDraw dessin;
	private JPanel left = new JPanel();
	private JPanel pCentre = new JPanel();
	private JProposition proposition;
	private JListePartie listePartie = new JListePartie();
	private JInformation info = new JInformation();
	private JInfoConnexion infoConnect = new JInfoConnexion();

	private JPanel pCentreHaut = new JPanel();
	private JPanel pCentreHaut_buttons = new JPanel();
	private JPanel pCentreHaut_colors = new JPanel();
	private JButton btnClearEcran = new JButton("Effacer écran");
	private JButton btnPret = new JButton("Prêt");
	// private JButton btnSelectColor = new JButton("Couleurs");

	private JBasePanel basePanel;
	private JLogoPanel logoPanel;
	
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
	
	private boolean isDrawer = false;

	public JClient(String titre) {
		
		super(titre);
		proposition = new JProposition();
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
		
		//Attribue la vue au client
		if(PictioLan.modele_gamer != null && PictioLan.modele_gamer.getGame() != null)
			PictioLan.modele_gamer.getGame().setClient(this);
	}

	private void initializate(){
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(500,400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("PictioLAN Client");
		this.setLayout(new BorderLayout());

		basePanel = new JBasePanel();
		basePanel.setLayout(new BorderLayout());

		logoPanel = new JLogoPanel();
		logoPanel.setPreferredSize(new Dimension(800,60));
		this.getContentPane().add(basePanel, BorderLayout.CENTER);
		this.getContentPane().add(logoPanel, BorderLayout.SOUTH);
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	/* Méthodes pour gérer le client */
	
	public void printGamers() {
		
		for(Gamer g : PictioLan.modele_gamer.getGame().getListGamers()) {
			listePartie.addMessage(g.getPseudo());
		}
	}
	
	public void printRandomWord(String word) {
		JOptionPane.showMessageDialog(this, "Word : " + word, "Your are the painter", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void setEnableDraw(boolean e) {
		//dessin.setEnabled(e);
	}
	
	public void setEnableChat(boolean e) {
		//proposition.setEnabled(e);
		//proposition.setActif(e);
	}
	
	public JProposition getChat() {
		return proposition;
	}
	
	public JDraw getDraw() {
		return dessin;
	}
	
	public void afficherTimer(int i) {
		info.setMessage("Timer : " + i + " sec");
	}
	
	/* Fin Méthodes pour gérer le client */
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void createLeft() {
		left.setOpaque(false);
		left.setLayout(new GridLayout(3, 1));
		left.add(infoConnect);
		left.add(listePartie);
		left.add(info);
	}

	private void createCenter() {
		dessin = new JDraw(this);

		pCentreHaut.setLayout(new GridLayout(2, 1));
		pCentreHaut.setOpaque(false);
		pCentreHaut_buttons.setLayout(new GridLayout(1, 2));
		pCentreHaut_colors.setLayout(new GridLayout(2, 6));
		pCentreHaut_colors.setOpaque(false);
		
		pCentreHaut_buttons.add(btnClearEcran);
		pCentreHaut_buttons.add(btnPret);
		btnPret.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
					
				dessin.setEnabled(true);
				
				if(PictioLan.modele_gamer != null && PictioLan.modele_gamer.getConnection() != null) {
					PictioLan.modele_gamer.getConnection().start_game_protocole();
					PictioLan.modele_gamer.launchChatDraw();
				}
			}
			
		});
		
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
//			//@Override
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
	
	public void setPoint(Vector<Point> points) {
		
//		points.clear();
//		points.add(new Point(153,152));
//		points.add(new Point(153,153));
//		points.add(new Point(155,153));
//		points.add(new Point(157,154));
//		points.add(new Point(158,154));
//		points.add(new Point(158,155));
//		points.add(new Point(160,155));
//		points.add(new Point(161,155));
//		points.add(new Point(162,156));
//		points.add(new Point(163,156));
//		points.add(new Point(164,156));
//		points.add(new Point(165,156));
//		points.add(new Point(167,156));
//		points.add(new Point(169,156));
//		points.add(new Point(171,157));
//		points.add(new Point(174,158));
//		points.add(new Point(177,159));
//		points.add(new Point(178,160));
//		points.add(new Point(179,161));
//		points.add(new Point(182,161));
//		points.add(new Point(183,161));
//		points.add(new Point(184,162));
//		points.add(new Point(186,162));
//		points.add(new Point(188,162));
//		points.add(new Point(190,162));
//		points.add(new Point(191,162));
//		points.add(new Point(193,162));
//		points.add(new Point(195,162));
//		points.add(new Point(197,163));
//		points.add(new Point(199,163));
//		points.add(new Point(201,163));
//		points.add(new Point(203,163));
//		points.add(new Point(205,163));
//		points.add(new Point(206,163));
//		points.add(new Point(209,163));
//		points.add(new Point(212,163));
//		points.add(new Point(213,162));
//		points.add(new Point(215,162));
//		points.add(new Point(217,162));
//		points.add(new Point(218,161));
//		points.add(new Point(220,161));
//		points.add(new Point(222,161));
//		points.add(new Point(224,161));
//		points.add(new Point(226,161));
//		points.add(new Point(227,161));
//		points.add(new Point(229,161));
//		points.add(new Point(232,160));
//		points.add(new Point(235,160));
//		points.add(new Point(238,160));
//		points.add(new Point(241,159));
//		points.add(new Point(245,159));
//		points.add(new Point(250,158));
//		points.add(new Point(252,158));
//		points.add(new Point(255,158));
//		points.add(new Point(257,158));
//		points.add(new Point(259,157));
//		points.add(new Point(260,157));
//		points.add(new Point(261,157));
//		points.add(new Point(262,157));
//		points.add(new Point(263,157));
//		points.add(new Point(265,157));
//		points.add(new Point(266,157));
//		points.add(new Point(268,157));
//		points.add(new Point(269,157));
//		points.add(new Point(269,157));
		
		for(Point p : points) {
			dessin.addPoint(p);
		}
	}
	
	public Vector<Point> getDrawedPoint() {
		
		Vector<Point> test = dessin.getAllPoints();
		
		System.out.print("Interface : ");
		for(Point p : test) {
			System.out.print("(" + p.x + "," + p.y + ")");
		}
		
		System.out.println();
		
		return test;
	}
	
	class JBasePanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/blueBackground.jpg")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}
	
	class JLogoPanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/pictioLAN_logo.png")).getImage();
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}
}
