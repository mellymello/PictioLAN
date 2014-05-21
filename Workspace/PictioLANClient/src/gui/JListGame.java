package gui;

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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JListGame extends JFrame{


	public JListGame (String title)
	{
		this.setSize(Configuration.LARGEUR_CLIENT,Configuration.HAUTEUR_CLIENT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.setTitle(title);
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pList = new JPanel();
		getContentPane().add(pList, BorderLayout.CENTER);
		pList.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JList list = new JList();
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
		
		JButton btnCreateGame = new JButton("Create Game");
		pGame.add(btnCreateGame);
		btnCreateGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JCreateGame createGame = new JCreateGame("Create the game");
			}
		});
		
		this.setVisible(true);
	}

}
