package connection;

import java.awt.Frame;
import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import game.Game;
import game.Gamer;
import gui.JWelcome;

public class PictioLan {
	
	static public Gamer modele_gamer;
	
	private JWelcome welcome;
	
	public PictioLan() {
		modele_gamer = new Gamer();
		welcome = new JWelcome();		
	}
	
	public static void main (String[] args) {
		
		PictioLan p = new PictioLan();
		
		
//		modele_gamer = new Gamer();
//		modele_gamer.launchConnexion("127.0.0.1");
//		modele_gamer.getConnection().auth_anonymous_protocole();
//		
//		//modele_gamer.getConnection().create_game_protocole(2, false, 4, "Animaux");
//		modele_gamer.getConnection().join_game_protocole(new Game(0,"Animaux",4,false,"Anonyme_0",2), 0);
//		
//		modele_gamer.getConnection().start_game_protocole();
//		modele_gamer.launchChatDraw();
//		
//		
////		do {
//		modele_gamer.getChat().addMessageToBuffer("UN MESSAGE !");
//			//modele_gamer.getDraw().sendMessage(new Point(1,1));
//			
////		}while(s.equals("STOP"));
	}
}
