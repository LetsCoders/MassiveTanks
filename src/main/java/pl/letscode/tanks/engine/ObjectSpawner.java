package pl.letscode.tanks.engine;

import java.util.Random;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.engine.objects.Position;
import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.engine.objects.bullet.BulletObjectFactory;
import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.engine.objects.tank.TankObjectFactory;
import pl.letscode.tanks.map.Coord;
import pl.letscode.tanks.map.MapMatrix;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainObjectFactory;
import pl.letscode.tanks.map.terrain.TerrainType;
import pl.letscode.tanks.player.Player;

/**
 * Service
 * @author edhendil
 *
 */
public class ObjectSpawner {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ObjectSpawner.class);

	private static final int MIN_BRICKS = 80;
	
	private final BulletObjectFactory bulletFactory;
	private final TankObjectFactory tankFactory;
	private final TerrainObjectFactory objectFactory;

	@Inject
	public ObjectSpawner(BulletObjectFactory bulletFactory,
			TankObjectFactory tankFactory, TerrainObjectFactory objectFactory) {
		this.bulletFactory = bulletFactory;
		this.tankFactory = tankFactory;
		this.objectFactory = objectFactory;
	}

	public TankObject spawnTank(Player player, Game game) {
		int x = 0;
		int y = 0;
		// double max = 500;
		Random random = new Random();
		MapMatrix gameBoard = game.getBoard();
		TankObject newTank = tankFactory.buildTank(player);
		do {
			x = random.nextInt(gameBoard.getHeight() + 1);
			y = random.nextInt(gameBoard.getWidth() + 1);
			newTank.setPosition(new Position(x * 32, y * 32));
		} while (gameBoard.getTerrainObject(new Coord(x, y)) != null
				|| game.willCollide(newTank));
		game.addTank(newTank);
		return newTank;
	}

	public BulletObject spawnBullet(TankObject tank, Game game) {
		BulletObject bullet = bulletFactory.buildBullet(tank);
		game.addBullet(bullet);
		return bullet;
	}

	public TerrainObject spawnNewTerrain(Game game) {
		MapMatrix gameBoard = game.getBoard();
		if (gameBoard.getBrickAmount() > MIN_BRICKS) {
			return null;
		}
		int x = 0;
		int y = 0;
		LOGGER.info("Start respawn");
		Random random = new Random();
		TerrainObject brickBlock = objectFactory
				.buildTerrainBlock(TerrainType.BRICK);
		do {
			x = random.nextInt(gameBoard.getHeight()) + 1;
			y = random.nextInt(gameBoard.getWidth()) + 1;
			brickBlock.setPosition(new Position(x * 32, y * 32));
		} while (gameBoard.getTerrainObject(new Coord(x, y)) != null
				|| game.willCollide(brickBlock));
		game.addTerrainBlock(brickBlock, x, y);
		LOGGER.info("Regenerating new brick {} to {}/{}", brickBlock, x, y);
		return brickBlock;
	}

}
