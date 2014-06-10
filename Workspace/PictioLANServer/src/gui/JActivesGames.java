package gui;


import game.Game;
import gamer.ManageGamer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JActivesGames extends JPanel {

	// private ManageGamer manager;
	private LinkedList<Game> actGames = Game.getGamesList();
	
	private JList<Integer> activesGamesID;
	private JScrollPane gamesListPanel;

	private JPanel buttonPanel;
	private GameInfoPanel gameInfoP;

	private JButton deleteButton;
	private JButton refreshButton;

	public JActivesGames() {

		this.setLayout(new BorderLayout());
		activesGamesID = new JList<Integer>();

		setGamesList();

		gamesListPanel = new JScrollPane(activesGamesID);

		buttonPanel = new JPanel(new FlowLayout());
		deleteButton = new JButton("Delete Game");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((activesGamesID.getSelectedValue()) != null) {
					System.out.println("TOOOO DOOO DELETE A GAME");
					// ManageGamer.deleteGamer(activesGames.getSelectedValue());
					setGamesList();
				} else {
					JOptionPane.showMessageDialog(null,
							"You have to select a game !", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setGamesList();

			}
		});
		buttonPanel.add(deleteButton);
		buttonPanel.add(refreshButton);

		gameInfoP = new GameInfoPanel();
	
		
		this.add(gamesListPanel, BorderLayout.CENTER);
		this.add(gameInfoP,BorderLayout.EAST);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setGamesList() {
	
		actGames =Game.getGamesList();
		
		Integer[] tmp = new Integer[actGames.size()];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = actGames.get(i).getGameID();
		}
		
		activesGamesID.setListData(tmp);
	}
	
	class GameInfoPanel extends JPanel{
		
		private JLabel idL;
		private JLabel roundsL;
		private JLabel categoryL;
		private JLabel creatorNameL;
		private JLabel gamersL;
		
		private JLabel idVal;
		private JLabel roundsVal;
		private JLabel categoryVal;
		private JLabel creatorNameVal;
		private JList<String> gamers;
		private JScrollPane gamersListPanel;
		
		public GameInfoPanel(){
			setLayout(new GridLayout(5,2));
			
			
			idL = new JLabel("ID :");
			roundsL = new JLabel("Round :");
			categoryL = new JLabel("Category :");
			creatorNameL = new JLabel("Created by :");
			gamersL = new JLabel("Gamers : ");
			
			
			idVal = new JLabel("");
			roundsVal = new JLabel("");
			categoryVal = new JLabel("");
			creatorNameVal = new JLabel("");
			gamers = new JList<String>();
			gamersListPanel = new JScrollPane(gamers);
			
			
			add(idL);
			add(idVal);
			add(roundsL);
			add(roundsVal);
			add(categoryL);
			add(categoryVal);
			add(creatorNameL);
			add(creatorNameVal);
			add(gamersL);
			add(gamersListPanel);
	
			
			
		}
		
		private void setGameInfo(int gameID){
			for (Game g : actGames) {
				if(g.getGameID()==gameID){
					idVal.setText(String.valueOf(gameID));
					roundsVal.setText(String.valueOf(g.getNbrRounds()));
					categoryVal.setText(g.getCategory());
					creatorNameVal.setText(g.getCreator().getPseudo());
					String[] tmp = ManageGamer.getGamers().toArray(new String[0]);
					gamers.setListData(tmp);
				}
			}
		}
	}

}
