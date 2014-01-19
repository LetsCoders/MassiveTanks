package pl.letscode.tanks.engine;

import javax.inject.Inject;

import org.dyn4j.dynamics.World;

import pl.letscode.tanks.engine.model.GameModelFactory;
import pl.letscode.tanks.engine.world.WorldFactory;
import pl.letscode.tanks.map.terrain.TerrainObject;

/**
 * Objects created with this factory are synced. World object does not need to
 * be updated explicitly with Body references.
 * 
 * @author edhendil
 * 
 */
public class GameFactory {

	private static final int MAP_HEIGHT = 40;
	private static final int MAP_WIDTH = 40;

	private final GameModelFactory modelFactory;
	private final WorldFactory worldFactory;

	@Inject
	public GameFactory(GameModelFactory modelFactory, WorldFactory worldFactory) {
		this.modelFactory = modelFactory;
		this.worldFactory = worldFactory;
	}

	/**
	 * I don't like it. There has to a better way to keep World and GameModel in
	 * sync.
	 * 
	 * @param id
	 * @return
	 */
	public Game createGame(long id) {
		World world = this.worldFactory.createNoGravityWorld();
		Game game = new Game(id, this.modelFactory.createRandomMap(MAP_HEIGHT,
				MAP_WIDTH), world);
		for (TerrainObject terrain : game.getBoard().getBlocks())
			world.addBody(terrain.getBody());
		return game;
	}

}
