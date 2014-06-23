package gui;

import game.Gamer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import connection.PictioLan;


public class JClient extends JFrame implements Configuration {
	private JDraw dessin;
	private JPanel left = new JPanel();
	private JPanel pCentre = new JPanel();
	private JProposition proposition;
	private JGamer gamerPartie = new JGamer();
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
<<<<<<< HEAD
=======
		
	/* Méthodes pour gérer le client */
	
	public void printGamers() {
		
		for(Gamer g : PictioLan.modele_gamer.getGame().getListGamers()) {
			gamerPartie.addMessage(g.getPseudo());
		}
	}
>>>>>>> 0e4fa7e5c7c0ab05d6f89375925d50845c965cf9
	
	/* Méthodes pour gérer le client */

	public void printRandomWord(String word) {
		JOptionPane.showMessageDialog(this, "Word : " + word, "Your are the painter", JOptionPane.PLAIN_MESSAGE);
	}

	public JProposition getChat() {
		return proposition;
	}
	
	public JDraw getDraw() {
		return dessin;
	}
	
	public void printGamers() {
		for(Gamer g : PictioLan.modele_gamer.getGame().getListGamers()) {
			listePartie.addMessage(g.getPseudo());
		}
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
		left.add(gamerPartie);
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
				if(PictioLan.modele_gamer != null && PictioLan.modele_gamer.getConnection() != null) {
					PictioLan.modele_gamer.getConnection().start_game_protocole();
					PictioLan.modele_gamer.launchChatDraw();
				}
			}
			
		});
		
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
//		try {
//			PictioLan.modele_gamer.getDraw().sendMessage(point);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
