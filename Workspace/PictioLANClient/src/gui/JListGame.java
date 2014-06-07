package gui;

import game.Game;
import gui.JConnect.ConnectPanel;
import gui.JConnect.InputPanel;

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
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import connection.ConnectionServer;



public class JListGame extends JFrame{

	private LinkedList<Game> games;
	
	
	private ListGamePanel basePanel;
	private GameInfoPanel infoPanel;
	private JPanel buttonPanel;

	private JButton join;
	private JButton refresh;
	
	private JPanel exitPanel;

	private JButton exitButton;

	private ConnectionServer connServer;
	
	public JListGame (ConnectionServer conn)
	{
		connServer = conn;
//		games=connServer.listGame();
		
		//for testing.......................................................
		
		games=new LinkedList<Game>();
		games.add(new Game(1, true, "test1"));
		games.add(new Game(2, false, "aaa"));
		games.add(new Game(3, false, "JKLJ"));
		games.add(new Game(4, true, "long name test AAAbbbbcccd"));
		
		//.......................
		
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
		buttonPanel= new JPanel();
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

		join= new JButton("JOIN");
		refresh= new JButton("Refresh");
		
		buttonPanel.add(refresh);
		buttonPanel.add(join);
		


		
		basePanel.add(infoPanel,BorderLayout.CENTER);
		basePanel.add(buttonPanel,BorderLayout.PAGE_END);
		
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
		private JScrollPane  gamesNameScroll;
		
		private JPanel rigthP;
		private JLabel test = new JLabel("TEEESSST");
		
		public GameInfoPanel() {
//			setOpaque(false);
			setLayout(new GridBagLayout());

			
			gamesNameList= new JList<String>();
			gamesNameScroll= new JScrollPane(gamesNameList);		
			setGameNameList();
			
			rigthP= new JPanel();
			rigthP.add(test);
			

			GridBagConstraints c = new GridBagConstraints();

			c.insets = new Insets(0, 0, 0, 0);
			c.weighty = 1;
//			c.gridwidth = 5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
//			c.fill=GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			add(gamesNameScroll, c);
			

			c.insets = new Insets(0, 0, 0, 0);
//			c.weighty = 1;
//			c.gridwidth = 5;
//			c.anchor = GridBagConstraints.FIRST_LINE_START;
//			c.fill=GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 0;
			add(rigthP, c);
			


		}
		
		
		
		//////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////
		//here we have to set NAME and not the PseudoCreator !
		///
		private void setGameNameList() {
			String [] tmp = new String [games.size()];
			
			for(int i=0; i< games.size(); i++){
				tmp[i]= games.get(i).getPseudoCreator();
			}
			
			gamesNameList.setListData(tmp);
		}
	}
	
	

}
