package pl.letscode.tanks.events.server.json;

public class ScoreUpdateDTO implements ServerData {

	private String playerName;
	private int newScore;
	
	public ScoreUpdateDTO(String playerName, int newScore) {
		this.playerName = playerName;
		this.newScore = newScore;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getNewScore() {
		return newScore;
	}
	
}
