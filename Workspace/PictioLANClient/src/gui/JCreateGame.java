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

		private JComboBox<Integer> txtNombreDeJoueurs;
		private JLabel nbrGamers = new JLabel("Number of Players");
		private ButtonGroup groupe = new ButtonGroup();
		private JRadioButton rdbtnSingle = new JRadioButton("Single");
		private JRadioButton rdbtnMulti = new JRadioButton("Multi");

		private JLabel errorLabel;

		public InputPanel() {
			setOpaque(false);
			setLayout(new GridBagLayout());

			errorLabel = new JLabel(" ");
			errorLabel.setFont(new Font(errorLabel.getFont().getName(),
					Font.BOLD, 14));
			errorLabel.setForeground(Color.YELLOW);

			nbrGamers.setForeground(Color.WHITE);
			nbrGamers.setFont(new Font(nbrGamers.getFont().getName(),
					Font.BOLD, 14));
			txtNombreDeJoueurs = new JComboBox<Integer>(new Integer[] { 1, 2,
					3, 4, 5, 6, 7, 8 });
			txtNombreDeJoueurs.setSelectedIndex(3);
			txtNombreDeJoueurs.setEditable(true);

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
					Integer val = 1;
					try {
						val = (Integer) txtNombreDeJoueurs.getSelectedItem();

						JCreateGame.this.conn.createGame(val,
								rdbtnMulti.isSelected());
					} catch (ClassCastException e) {
						errorLabel.setText("Give a number !");
					}

				}
			});

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(10, 80, 0, 10);
			c.gridx = 0;
			c.gridy = 0;
			add(nbrGamers, c);

			c.insets = new Insets(10, 80, 0, 10);
			c.gridx = 0;
			c.gridy = 1;
			add(txtNombreDeJoueurs, c);

			c.insets = new Insets(10, 0, 0, 0);
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 1;
			add(rdbtnSingle, c);

			c.insets = new Insets(10, 0, 0, 0);
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 1;
			add(rdbtnMulti, c);

			c.insets = new Insets(10, 10, 0, 0);
			c.gridwidth = 1;
			c.gridx = 4;
			c.gridy = 1;
			add(btnStart, c);

			c.insets = new Insets(5, 80, 0, 0);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_END; // bottom of space
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 2;
			add(errorLabel, c);

		}
	}

}
