package gui;

import game.Gamer;
import gui.JConnect.ConnectPanel;
import gui.JConnect.InputPanel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JTextField;
import javax.swing.JButton;

import connection.ConnectionServer;

public class JSubscribe extends Observable {

	private JFrame frame;

	private SubscribePanel basePanel;
	private UserDataPanel inputPanel;

	private JPanel exitPanel;

	private JButton exitButton;

	private ConnectionServer connServer;

	/**
	 * Create the application.
	 */

	public JSubscribe(JConnect w, ConnectionServer c) {

		connServer = c;

		initialize();

		frame.setVisible(true);
		frame.pack();
		this.addObserver(w);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.setPreferredSize(new Dimension(600, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setLayout(new BorderLayout());

		frame.setTitle("Subscribe");

		basePanel = new SubscribePanel();
		inputPanel = new UserDataPanel();

		exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		exitButton = new JButton("X");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers();
				frame.dispose();

			}
		});
		exitPanel.add(exitButton);

		frame.getContentPane().add(exitPanel, BorderLayout.NORTH);

		MoveMouseListener mml = new MoveMouseListener(basePanel);
		frame.addMouseMotionListener(mml);
		frame.addMouseListener(mml);

		frame.getContentPane().add(basePanel, BorderLayout.CENTER);
		basePanel.setLayout(new BorderLayout());

		basePanel.add(inputPanel, BorderLayout.CENTER);

	}

	class SubscribePanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/subscribe_backGround.png")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}

	class UserDataPanel extends JPanel {

		private JLabel pseudoLabel;
		private JLabel passLabel;
		private JLabel passConfirmLabel;
		private JLabel emailLabel;

		private JTextField txtPseudo;
		private JPasswordField passField;
		private JPasswordField passConfirmField;
		private JTextField emailField;

		private JButton okButton;
		
		private JLabel errorLabel;

		public UserDataPanel() {
			setOpaque(false);
			setLayout(new GridBagLayout());

			pseudoLabel = new JLabel("Pseudo : ");
			pseudoLabel.setForeground(Color.WHITE);

			txtPseudo = new JTextField();
			txtPseudo.setColumns(12);

			passLabel = new JLabel("Password : ");
			passLabel.setForeground(Color.WHITE);

			passField = new JPasswordField();
			passField.setColumns(12);

			passConfirmLabel = new JLabel("Confirm Password : ");
			passConfirmLabel.setForeground(Color.WHITE);

			passConfirmField = new JPasswordField();
			passConfirmField.setColumns(12);

			emailLabel = new JLabel("Email : ");
			emailLabel.setForeground(Color.WHITE);

			emailField = new JTextField();
			emailField.setColumns(12);

			errorLabel = new JLabel(" ");
			errorLabel.setFont(new Font(errorLabel.getFont().getName(),Font.BOLD,14));
			errorLabel.setForeground(Color.YELLOW);
			
			okButton = new JButton("OK !");

			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					errorLabel.setText(" ");
					String email;
					String pseudo = txtPseudo.getText();
					if (pseudo.isEmpty()) {
						errorLabel.setText("give a Pseudo");
					} else {
						String pass = new String(passField.getPassword());
						String confirmPass = new String(passConfirmField
								.getPassword());
						if(pass.isEmpty()){
							errorLabel.setText("give a password");
						}
						else if (pass.compareTo(confirmPass) != 0) {
							errorLabel.setText("passwords not matching");
						} else {
							email = emailField.getText();

							if (email.isEmpty() || (!email.contains("@") || !email.contains("."))) {
								errorLabel.setText("give a valid email");
							} else {
								// Connexion au serveur !!
								if (connServer.getConnexionDone() == false) {
									connServer.setIP("127.0.0.1");
									connServer.launchConnexion();
								}
								// Connexion au serveur !!
								// Subscribe
								connServer.setPseudo(pseudo);
								connServer.setPassword(pass);
								connServer.setEmail(email);
								connServer.authentification("AUTH_SUBSCRIBE");
								
								Gamer gamerTest = connServer.getGamer();
								if (gamerTest == null) {
									errorLabel.setText("Pseudo already in use.");
								}
								else {
									setChanged();
									notifyObservers();
									frame.dispose();
								}
							}
						}
					}
				}
			});

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(5, 0, 0, 0);
			c.gridx = 0;
			c.gridy = 0;
			add(pseudoLabel, c);

			c.gridx = 1;
			c.gridy = 0;
			add(txtPseudo, c);

			c.gridx = 0;
			c.gridy = 1;
			add(passLabel, c);

			c.gridx = 1;
			c.gridy = 1;
			add(passField, c);

			c.gridx = 0;
			c.gridy = 2;
			add(passConfirmLabel, c);

			c.gridx = 1;
			c.gridy = 2;
			add(passConfirmField, c);

			c.gridx = 0;
			c.gridy = 3;
			add(emailLabel, c);

			c.gridx = 1;
			c.gridy = 3;
			add(emailField, c);

			c.insets = new Insets(5, 20, 0, 0);
			c.gridheight = 2;
			c.gridx = 2;
			c.gridy = 1;
			add(okButton, c);
			
			c.insets = new Insets(5, 0, 0, 0);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_END; //bottom of space
			c.gridwidth=2;
			c.gridx = 1;
			c.gridy = 4;
			add(errorLabel, c);

		}
	}

}
