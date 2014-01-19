package pl.letscode.tanks.player;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PlayerNameGenerator {

	private SecureRandom random;

	public PlayerNameGenerator() {
		this.random = new SecureRandom();
	}

	public String getNext() {
		return new BigInteger(130, random).toString(16).substring(0, 8);
	}

}
