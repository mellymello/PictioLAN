package gui;

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
import java.util.Observer;

import javax.swing.JTextField;
import javax.swing.JButton;

import connection.PictioLan;


public class JConnect extends JFrame implements Observer{

	private ConnectPanel basePanel;
	private InputPanel inputPanel;
	private JPanel exitPanel;
	private JButton exitButton;

	public JConnect() {
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

		basePanel = new ConnectPanel();
		inputPanel = new InputPanel();

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

		basePanel.add(inputPanel, BorderLayout.CENTER);
	}

	class ConnectPanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/login_backGround.jpg")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}
	}

	class InputPanel extends JPanel {

		private JLabel pseudoLabel;
		private JLabel passLabel;
		private JTextField txtPseudo;
		private JPasswordField passField;

		private JLabel subscribeLabel;

		private JButton loginButton;
		
		private JLabel errorLabel;

		public InputPanel() {
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

			loginButton = new JButton("Login");
			
			errorLabel = new JLabel(" ");
			errorLabel.setFont(new Font(errorLabel.getFont().getName(),Font.BOLD,14));
			errorLabel.setForeground(Color.YELLOW);

			loginButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					String pseudo = txtPseudo.getText();
					String pass = new String(passField.getPassword());
					
					if (PictioLan.modele_gamer.getConnection() == null)
						return;

					if(PictioLan.modele_gamer.getConnection().auth_connect_protocole(pseudo, pass)) {
						JListGame listGame = new JListGame();
						listGame.setVisible(true);
						JConnect.this.dispose();
					}
					else {
						errorLabel.setText("Wrong login/password");
					}
				}
			});

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(10, 80, 0, 10);
			c.gridx = 0;
			c.gridy = 0;
			add(pseudoLabel, c);

			c.insets = new Insets(10, 0, 0, 0);
			c.gridwidth = 2;
			c.gridx = 1;
			c.gridy = 0;
			add(txtPseudo, c);

			c.insets = new Insets(10, 80, 0, 10);
			c.gridx = 0;
			c.gridy = 1;
			add(passLabel, c);

			c.insets = new Insets(10, 0, 0, 0);
			c.gridwidth = 2;
			c.gridx = 1;
			c.gridy = 1;
			add(passField, c);

			c.insets = new Insets(10, 80, 0, 0);
			c.gridx = 2;
			c.gridy = 2;
			add(loginButton, c);

			subscribeLabel = new JLabel("<HTML><U>Subscribe<U><HTML>");

			subscribeLabel.setForeground(Color.BLUE);

			subscribeLabel.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					JSubscribe subscribe = new JSubscribe(JConnect.this);
					JConnect.this.setEnabled(false);
				}
			});

			c.insets = new Insets(10, 0, 0, 40);

			c.gridx = 1;
			c.gridy = 2;
			add(subscribeLabel, c);
			
			c.insets = new Insets(5, 0, 0, 0);
			c.gridx = 0;
			c.gridy = 2;
			add(errorLabel, c);

		}
	}

	public void update(Observable o, Object arg) {
		this.setEnabled(true);
	}

}
