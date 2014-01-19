package pl.letscode.tanks.map.terrain.grass;

import org.dyn4j.dynamics.Body;

public final class GrassBrickFactory {

	private GrassBrickFactory() {
		// private
	}

	public static GrassBlock getBlock(final long id) {
		Body grassBody = new Body();
		GrassBlock grassBlock = new GrassBlock(grassBody, id);
		grassBody.setUserData(grassBlock);
		return grassBlock;
	}

}
