package game;

public class Round {

	Game game = null;
	Gamer drawer = null;
	String word = "";
	Gamer winner = null;
	
	public Round(Game g, Gamer d, String w) {
		game = g;
		drawer = d;
		word = w;
	}

	public Game getGame() {
		return game;
	}

	public Gamer getDrawer() {
		return drawer;
	}

	public String getWord() {
		return word;
	}
	
	public void setWinner(Gamer win) {
		winner = win;
	}

	public Gamer getWinner() {
		return winner;
	}
	
}
