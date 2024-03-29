package gui;

import game.Gamer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connection.PictioLan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class JWelcome extends JFrame{

	private JWelcomePanel basePanel;
	private JServerInput inputPanel;
	private JPanel exitPanel;
	private JButton exitButton;

	public JWelcome() {
		initialize();
		this.setVisible(true);
		this.pack();
	}

	private void initialize() {

		this.setPreferredSize(new Dimension(600, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());

		this.setTitle("Connect to our db");

		basePanel = new JWelcomePanel();
		inputPanel = new JServerInput();

		exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		exitButton = new JButton("X");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		exitPanel.add(exitButton);

		this.getContentPane().add(exitPanel, BorderLayout.NORTH);

		JMoveMouseListener mml = new JMoveMouseListener(basePanel);
		this.addMouseMotionListener(mml);
		this.addMouseListener(mml);

		this.getContentPane().add(basePanel, BorderLayout.CENTER);
		basePanel.setLayout(new BorderLayout());

		basePanel.add(inputPanel, BorderLayout.EAST);
	}
	
	
	class JWelcomePanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource("/img/welcome.jpg")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}
	}

	class JServerInput extends JPanel {
		
		private JLabel ipLabel;
		private JTextField ipField;
		
		private JCheckBox anonymousCheck;

		private JButton connect;

		public JServerInput() {
			
			setOpaque(false);
			setLayout(new GridBagLayout());

			ipLabel = new JLabel("Server IP : ");
			ipLabel.setForeground(Color.WHITE);

			ipField = new JTextField();
			ipField.setColumns(12);
			ipField.setText("127.0.0.1");

			anonymousCheck = new JCheckBox("Anonymous Login");
			anonymousCheck.setOpaque(false);
			
			connect = new JButton("Connect");

			connect.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					//Action du bouton Connect
					String text = ipField.getText();
					boolean isIpValide = true;
					
					try {
						InetAddress ip = InetAddress.getByName(text);
					}
					catch (UnknownHostException uhe) {
						ipField.setText("");
						isIpValide = false;
					}
					
					if (isIpValide == true && ! text.equals("")) {
						
						if(anonymousCheck.isSelected()) {
							
							PictioLan.modele_gamer.launchConnexion(text);
							PictioLan.modele_gamer.getConnection().auth_anonymous_protocole();
							JListGame listGame = new JListGame();
						}
						else {
							PictioLan.modele_gamer.launchConnexion(text);
							JConnect connect = new JConnect();
						}
						
						JWelcome.this.dispose();
					}
				}
				
			});

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(10, 0, 0, 10);
			c.gridx = 0;
			c.gridy = 0;
			add(ipLabel, c);

			c.insets = new Insets(10, 0, 0, 80);
			c.gridx = 1;
			c.gridy = 0;
			add(ipField, c);

			c.insets = new Insets(10, 0, 0, 80);
			c.gridx = 1;
			c.gridy = 1;
			add(anonymousCheck, c);
			

			c.insets = new Insets(10, 0, 0, 80);
			c.gridx = 1;
			c.gridy = 2;
			add(connect, c);
		}
	}

}






