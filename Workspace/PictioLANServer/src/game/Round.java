package game;

public class Round {

	Game game;
	Gamer drawer;
	
	String word;
	
	Gamer winner;
	
	public Round(Game g, Gamer d, String w) {
		game = g;
		drawer = d;
		word = w;
	}
	
	public Gamer getDrawer() {
		return drawer;
	}
	
	public String getWord() {
		return word;
	}
	
	public Gamer getWinner() {
		return winner;
	}
	
	
	public void setWinner(Gamer g) {
		winner = g;
	}
}
