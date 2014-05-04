package game;

public class Gamer {

	String pseudo;
	boolean gameCreator;
	
	public Gamer() {}

	public Gamer(String p, boolean c) {
		pseudo = p;
		gameCreator = c;
	}
	
	public void setPseudo(String p) {
		pseudo = p;
	}
	
	public void setCreateurPartie(boolean isCreator) {
		gameCreator = isCreator;
	}
	
	
}
