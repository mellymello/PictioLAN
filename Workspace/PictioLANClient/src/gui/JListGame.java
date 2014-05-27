package gui;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import configuration.Configuration;
import connection.ConnectionServer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class JListGame extends JFrame{

	LinkedList<Game> games;
	LinkedList<String> listGameTitre;
	
	ConnectionServer conn;
	
	JList list;
	
	public JListGame (String title, ConnectionServer conn)
	{
		this.setVisible(true);
		
		this.conn = conn;
//		games = conn.listGame();
		
//		listGameTitre = new LinkedList<String>();
//		
//		for(Game g : games) {
//			String s = g.getPseudoCreator() + " ";
//			
//			if(g.isModeEquipe())
//				s += "Jeu en équipe";
//			else
//				s += "Jeu solo";
//			
//			listGameTitre.add(s);
//		}
		
		this.setSize(Configuration.LARGEUR_CLIENT,Configuration.HAUTEUR_CLIENT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.setTitle(title);
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pList = new JPanel();
		getContentPane().add(pList, BorderLayout.CENTER);
		pList.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
//		if(listGameTitre != null)
//			list = new JList(listGameTitre.toArray());
//		else
			list = new JList();
		
		pList.add(list);
		

//		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//		list.setVisibleRowCount(-1);
//		JScrollPane listScroller = new JScrollPane(list);
//		listScroller.setPreferredSize(new Dimension(250, 80));
//		
		JPanel pGame = new JPanel();
		getContentPane().add(pGame, BorderLayout.SOUTH);
		pGame.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnJoinGame = new JButton("Join Game");
		pGame.add(btnJoinGame);
		btnJoinGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(list.isSelectionEmpty()) {
					JListGame.this.conn.joinGame(games.get(list.getSelectedIndex()));
				}
			}
		});
		
		JButton btnCreateGame = new JButton("Create Game");
		pGame.add(btnCreateGame);
		btnCreateGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				conn.createGame(nbGamer, mode);
				JCreateGame createGame = new JCreateGame("Create the game",JListGame.this.conn);
			}
		});
		
		
	}

}
