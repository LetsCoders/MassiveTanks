package pl.letscode.tanks.player;

import java.util.Objects;

public class Player {

	private String name;
	private int score;

	/* Constructors */
	
	public Player(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/* Read */
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	/* Commands */
	
	public void addFrag() {
		this.score++;
	}

	/* Utilities */
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (this == object)
			return true;
		if (object instanceof Player) {
			Player player = (Player) object;
			if (player.getName().equals(this.name))
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

}
