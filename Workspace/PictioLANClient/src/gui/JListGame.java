package gui;

import game.Game;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



import connection.ConnectionServer;

public class JListGame extends JFrame {

	private LinkedList<Game> games;

	private ListGamePanel basePanel;
	private GameInfoPanel infoPanel;
	private JPanel buttonPanel;

	private JPanel exitPanel;

	private JButton exitButton;

	private ConnectionServer connServer;

	public JListGame(ConnectionServer conn) {
		connServer = conn;
		// games=connServer.listGame();
		

		// for testing.......................................................

		games = connServer.listGame();

		// .......................

		initialize();
		this.setVisible(true);
		this.pack();
	}

	private void initialize() {

		this.setPreferredSize(new Dimension(700, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());

		basePanel = new ListGamePanel();
		basePanel.setLayout(new BorderLayout());

		infoPanel = new GameInfoPanel();
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

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

		MoveMouseListener mml = new MoveMouseListener(basePanel);
		this.addMouseMotionListener(mml);
		this.addMouseListener(mml);

		basePanel.add(infoPanel, BorderLayout.CENTER);
		basePanel.add(buttonPanel, BorderLayout.PAGE_END);

		this.getContentPane().add(basePanel, BorderLayout.CENTER);

	}

	class ListGamePanel extends JPanel {
		Image bg = new ImageIcon(getClass().getResource(
				"/img/wille_join_BackGround.png")).getImage();

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		}
	}

	class GameInfoPanel extends JPanel {

		private JList<String> gamesNameList;
		private JScrollPane gamesNameScroll;

		private Game selectetGame;

		private JButton join;
		private JButton refresh;
		
		private JButton createGame;

		private JPanel rigthP;
		private JLabel gameCreator;
		private JLabel gameMode;
		private JLabel gameRound;
		private JLabel nbrPlayer;
		private JLabel gameCategory;

		private JLabel gameCreatorValue;
		private JLabel gameModeValue;
		private JLabel gameRoundValue;
		private JLabel nbrPlayerValue;
		private JLabel gameCategoryValue;

		public GameInfoPanel() {
			setOpaque(false);
			setLayout(new GridBagLayout());

			gamesNameList = new JList<String>();
			gamesNameScroll = new JScrollPane(gamesNameList);
			setGameNameList();

			gamesNameList.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					try{
					int selected = Integer.valueOf(gamesNameList.getSelectedValue());

					for (int i = 0; i < games.size(); i++) {
						if (games.get(i).getIdGame() == selected) {
							selectetGame = games.get(i);
							break;
						}
					}

					refreshGameInfo();

				}catch(NumberFormatException nfe){
					
				}
				}
				
			});

			join = new JButton("JOIN !");
			refresh = new JButton("Refresh");
			createGame= new JButton("Create Game");
			
			join.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(selectetGame!=null) {
						connServer.joinGame(selectetGame);
					}
				}
			});
			
			refresh.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					games=connServer.listGame();
					setGameNameList();
					
				}
			});
			
			createGame.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JCreateGame createGame = new JCreateGame(connServer);
					setGameNameList();
				}
			});

			gameCreator = new JLabel("Created by : ");
			gameMode = new JLabel("Game mode : ");
			gameRound = new JLabel("Round : ");
			nbrPlayer = new JLabel("Players : ");
			gameCategory = new JLabel("Category :");

			gameCreator.setFont(new Font(gameCreator.getFont().getName(),
					Font.BOLD, 17));
			gameCreator.setForeground(Color.WHITE);

			gameMode.setFont(new Font(gameMode.getFont().getName(), Font.BOLD,
					17));
			gameMode.setForeground(Color.WHITE);

			gameRound.setFont(new Font(gameRound.getFont().getName(),
					Font.BOLD, 17));
			gameRound.setForeground(Color.WHITE);

			nbrPlayer.setFont(new Font(nbrPlayer.getFont().getName(),
					Font.BOLD, 17));
			nbrPlayer.setForeground(Color.WHITE);

			gameCategory.setFont(new Font(gameCategory.getFont().getName(),
					Font.BOLD, 17));
			gameCategory.setForeground(Color.WHITE);

			gameCreatorValue = new JLabel(" ");
			gameModeValue = new JLabel(" ");
			gameRoundValue = new JLabel(" ");
			nbrPlayerValue = new JLabel(" ");
			gameCategoryValue = new JLabel(" ");

			gameCreatorValue.setFont(new Font(gameCreatorValue.getFont()
					.getName(), Font.BOLD, 17));
			gameCreatorValue.setForeground(Color.WHITE);

			gameModeValue.setFont(new Font(gameModeValue.getFont().getName(),
					Font.BOLD, 17));
			gameModeValue.setForeground(Color.WHITE);

			gameRoundValue.setFont(new Font(gameRoundValue.getFont().getName(),
					Font.BOLD, 17));
			gameRoundValue.setForeground(Color.WHITE);

			nbrPlayerValue.setFont(new Font(nbrPlayerValue.getFont().getName(),
					Font.BOLD, 17));
			nbrPlayerValue.setForeground(Color.WHITE);

			gameCategoryValue.setFont(new Font(gameCategoryValue.getFont()
					.getName(), Font.BOLD, 17));
			gameCategoryValue.setForeground(Color.WHITE);

			rigthP = new JPanel();
			rigthP.setLayout(new GridLayout(6, 2, 15, 25));
			rigthP.setOpaque(false);

			rigthP.add(gameCreator);
			rigthP.add(gameCreatorValue);
			rigthP.add(gameMode);
			rigthP.add(gameModeValue);
			rigthP.add(gameRound);
			rigthP.add(gameRoundValue);
			rigthP.add(nbrPlayer);
			rigthP.add(nbrPlayerValue);
			rigthP.add(gameCategory);
			rigthP.add(gameCategoryValue);

			rigthP.add(refresh);
			rigthP.add(join);

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(220, 20, 0, 150);
			c.gridheight = 3;
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 2;
			c.anchor = GridBagConstraints.CENTER;
			c.ipadx = 200;
			c.ipady = 80;
			add(gamesNameScroll, c);
			
			c.insets = new Insets(30, 50, 0, 0);
			c.gridheight = 1;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 5;
			c.anchor = GridBagConstraints.NORTH;
			c.ipadx = 0;
			c.ipady = 0;
			add(createGame,c);

			c.insets = new Insets(90, 0, 0, 50);
			c.gridheight = 5;
			c.gridwidth = 2;
			c.gridx = 3;
			c.gridy = 1;
			c.anchor = GridBagConstraints.NORTHWEST;
			c.weighty = GridBagConstraints.VERTICAL;
			c.weightx= GridBagConstraints.WEST;
			c.ipadx = 20;
			c.ipady = 10;
			add(rigthP, c);

		}

		private void setGameNameList() {
			String[] tmp = new String[games.size()];

			for (int i = 0; i < games.size(); i++) {
				tmp[i] = String.valueOf(games.get(i).getIdGame());
			}

			gamesNameList.setListData(tmp);
		}
	
		
		private void refreshGameInfo() {
			if (selectetGame != null) {
				gameCreatorValue.setText(selectetGame.getPseudoCreator());
				gameCreatorValue.setToolTipText(gameCreatorValue.getText());

				if (selectetGame.isModeEquipe()) {
					gameModeValue.setText("Team game");
				} else {
					gameModeValue.setText("Solo game");
				}
				gameRoundValue.setText(String.valueOf(selectetGame
						.getNbrRound()));
				nbrPlayerValue.setText(String.valueOf(selectetGame.getNbrMaxGamers()));
				gameCategoryValue.setText(selectetGame.getCategory());
			}
		}
	}

}
