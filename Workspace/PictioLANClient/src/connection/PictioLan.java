package connection;

import java.awt.Frame;
import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import game.Game;
import game.Gamer;
import gui.JClient;
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
	}
}
