package pl.letscode.tanks.player;

import com.google.inject.Inject;

public class PlayerFactory {

	private final PlayerNameGenerator nameGenerator;

	@Inject
	public PlayerFactory(PlayerNameGenerator nameGenerator) {
		this.nameGenerator = nameGenerator;
	}

	public Player createRandomNamePlayer() {
		return new Player(this.nameGenerator.getNext(), 0);
	}

	public Player createPlayer(String name) {
		return new Player(name, 0);
	}

}
