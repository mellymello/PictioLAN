package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import connection.ConnectionServer;

public class JCreateGame extends JFrame {

	private ConnectionServer conn;

	private NewGame basePanel;
	private InputPanel inputPanel;

	private JPanel exitPanel;

	private JButton exitButton;

	/**
	 * 
	 * Create the application.
	 */
	public JCreateGame(ConnectionServer conn) {

		this.conn = conn;

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

		basePanel = new NewGame();
		inputPanel = new InputPanel();

		exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		exitButton = new JButton("X");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});
		exitPanel.add(exitButton);

		this.getContentPane().add(exitPanel, BorderLayout.NORTH);

		MoveMouseListener mml = new MoveMouseListener(basePanel);
		this.addMouseMotionListener(mml);
		this.addMouseListener(mml);

		this.getContentPane().add(basePanel, BorderLayout.CENTER);
		basePanel.setLayout(new BorderLayout());

		basePanel.add(inputPanel, BorderLayout.CENTER);

	}

	class NewGame extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/newGame_background.png")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}

	class InputPanel extends JPanel {

		private JComboBox<Integer> nbrPlayers;
		private JLabel nbrPlayerLabel = new JLabel("Number of Players");
		private ButtonGroup groupe = new ButtonGroup();
		private JRadioButton rdbtnSingle = new JRadioButton("Single");
		private JRadioButton rdbtnMulti = new JRadioButton("Multi");
		
		private JLabel nbrRoundsLabel = new JLabel("Number of Rounds");
		private JComboBox<Integer> nbrRounds;
		
		private JLabel categoryLabel = new JLabel("Category");
		private JComboBox<String> category;

		private JLabel errorLabel;

		public InputPanel() {
			setOpaque(false);
			setLayout(new GridBagLayout());

			errorLabel = new JLabel(" ");
			errorLabel.setFont(new Font(errorLabel.getFont().getName(),
					Font.BOLD, 14));
			errorLabel.setForeground(Color.YELLOW);

			nbrPlayerLabel.setForeground(Color.WHITE);
			nbrPlayerLabel.setFont(new Font(nbrPlayerLabel.getFont().getName(),
					Font.BOLD, 14));
			nbrPlayers = new JComboBox<Integer>(new Integer[] { 1, 2,
					3, 4, 5, 6, 7, 8 });
			nbrPlayers.setSelectedIndex(3);
			nbrPlayers.setEditable(true);
			
			nbrRoundsLabel.setForeground(Color.WHITE);
			nbrRoundsLabel.setFont(new Font(nbrRoundsLabel.getFont().getName(),
					Font.BOLD, 14));
			nbrRounds = new JComboBox<Integer>(new Integer[] { 1, 2,
					3, 4, 5, 6, 7, 8 });
			nbrRounds.setSelectedIndex(3);
			nbrRounds.setEditable(true);
	
			
			
			categoryLabel.setForeground(Color.WHITE);
			categoryLabel.setFont(new Font(categoryLabel.getFont().getName(),
					Font.BOLD, 14));
			category = new JComboBox<String>(conn.listCategory());
			category.setEditable(false);

			rdbtnMulti.setOpaque(false);
			rdbtnMulti.setForeground(Color.WHITE);
			rdbtnMulti.setFont(new Font(rdbtnMulti.getFont().getName(),
					Font.BOLD, 14));

			rdbtnSingle.setOpaque(false);
			rdbtnSingle.setForeground(Color.WHITE);
			rdbtnSingle.setFont(new Font(rdbtnSingle.getFont().getName(),
					Font.BOLD, 14));

			groupe.add(rdbtnSingle);
			groupe.add(rdbtnMulti);
			rdbtnSingle.setSelected(true);

			JButton btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Integer valPlayer = 1;
					Integer valRound = 1;
					String valCat="";
					try {
						valPlayer = (Integer) nbrPlayers.getSelectedItem();
						valRound = (Integer) nbrRounds.getSelectedItem();
						valCat = (String) category.getSelectedItem();
						
						JCreateGame.this.conn.createGame(valPlayer,
								rdbtnMulti.isSelected(),valRound, valCat);
					} catch (ClassCastException e) {
						errorLabel.setText("Give a number !");
						nbrPlayers.setSelectedIndex(3);
						nbrRounds.setSelectedIndex(3);
					}

				}
			});

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(55, 20, 0, 10);
			c.gridx = 1;
			c.gridy = 0;
			add(nbrPlayerLabel, c);

			c.insets = new Insets(15, 20, 0, 10);
			c.gridx = 1;
			c.gridy = 1;
			add(nbrPlayers, c);
			
			
			
			c.insets = new Insets(55, 20, 0, 10);
			c.gridx = 2;
			c.gridy = 0;
			add(nbrRoundsLabel, c);

			c.insets = new Insets(15, 20, 0, 10);
			c.gridx = 2;
			c.gridy = 1;
			add(nbrRounds, c);
			
			c.insets = new Insets(55, 20, 0, 10);
			c.gridx = 3;
			c.gridy = 0;
			add(categoryLabel, c);

			c.insets = new Insets(15, 20, 0, 10);
			c.gridx = 3;
			c.gridy = 1;
			add(category, c);
			
			

			c.insets = new Insets(10, 0, 0, 0);
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 2;
			add(rdbtnSingle, c);

			c.insets = new Insets(10, 0, 0, 0);
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 2;
			add(rdbtnMulti, c);

			c.insets = new Insets(10, 10, 0, 0);
			c.gridwidth = 1;
			c.gridx = 3;
			c.gridy = 2;
			add(btnStart, c);

			c.insets = new Insets(15, 0, 0, 0);
			c.fill = GridBagConstraints.WEST;
//			c.anchor = GridBagConstraints.LINE_START; // bottom of space
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 3;
			add(errorLabel, c);

		}
	}

}
