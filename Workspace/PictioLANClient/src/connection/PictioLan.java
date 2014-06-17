package connection;

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
	}
}
