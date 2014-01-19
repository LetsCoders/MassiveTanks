package pl.letscode.tanks.engine;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.engine.contact.BulletHitContactListener;
import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.events.server.json.DirectionChangedDTO;
import pl.letscode.tanks.events.server.json.GameUpdateDTO;
import pl.letscode.tanks.events.server.json.MapRespawnDTO;
import pl.letscode.tanks.events.server.json.PlayerQuitDTO;
import pl.letscode.tanks.events.server.json.PlayerStateDTO;
import pl.letscode.tanks.events.server.json.TankFireDTO;
import pl.letscode.tanks.events.server.json.TankRespawnDTO;
import pl.letscode.tanks.events.server.json.WorldStateDTO;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.player.Player;
import pl.letscode.tanks.player.PlayerRepository;

public class GameService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GameService.class);

	private final ObjectSpawner objectSpawner;
	private final GameRepository gameRepository;
	private final PlayerRepository playerRepository;

	@Inject
	public GameService(ObjectSpawner objectSpawner,
			GameRepository gameRepository, PlayerRepository playerRepository) {
		this.objectSpawner = objectSpawner;
		this.gameRepository = gameRepository;
		this.playerRepository = playerRepository;
	}

	/* Read */

	public Iterable<Player> getPlayers(long gameId) {
		return this.gameRepository.findOne(gameId).getPlayers();
	}

	/* Commands */

	public WorldStateDTO addNewPlayer(long gameId, Player player) {
		Game game = this.gameRepository.findOne(gameId);
		WorldStateDTO data = new WorldStateDTO(game.getPlayerStates(),
				new PlayerStateDTO(player, game.getTankByPlayer(player)),
				game.getBullets(), game.getBoard());
		game.addPlayer(player);
		this.playerRepository.save(player);
		LOGGER.info("new player added");
		return data;
	}

	public PlayerQuitDTO playerQuit(long gameId, Player player) {
		Game game = this.gameRepository.findOne(gameId);
		TankObject tank = game.getTankByPlayer(player);
		long tankId = 0;
		if (tank != null) {
			tankId = tank.getId();
			game.removeTank(tank);
		}
		game.removePlayer(player);
		this.playerRepository.delete(player);
		return new PlayerQuitDTO(player.getName(), tankId);
	}

	public TankFireDTO fireTankCannon(long gameId, Player player) {
		Game game = this.gameRepository.findOne(gameId);
		TankObject tank = game.getTankByPlayer(player);
		if (tank != null) {
			BulletObject bullet = this.objectSpawner.spawnBullet(tank, game);
			return new TankFireDTO(bullet.getSourceTank().getId(),
					bullet.getPosition(), bullet.getId(),
					bullet.getMovementSpeed(), bullet.getDirection());
		}
		return null;
	}

	public TankRespawnDTO respawnTank(long gameId, Player player) {
		Game game = this.gameRepository.findOne(gameId);
		if (game.getTankByPlayer(player) == null) {
			TankObject tank = this.objectSpawner.spawnTank(player, game);
			return new TankRespawnDTO(player.getName(), tank);
		}
		return null;
	}

	public DirectionChangedDTO changeTankDirection(long gameId, Player player,
			Axis2D direction) {
		Game game = this.gameRepository.findOne(gameId);
		TankObject tank = game.getTankByPlayer(player);
		if (tank != null) {
			boolean directionChanged = tank.changeTankDirection(direction);
			if (directionChanged) {
				return new DirectionChangedDTO(tank.getId(),
						tank.getMovementDirection(), tank.getFacingDirection(),
						tank.getPosition());
			}
		}
		return null;
	}

	public MapRespawnDTO regenerateMap(long gameId) {
		Game game = this.gameRepository.findOne(gameId);
		TerrainObject newTerrain = this.objectSpawner.spawnNewTerrain(game);
		LOGGER.info("Sent terrain respawn event");
		return newTerrain != null ? new MapRespawnDTO(newTerrain) : null;
	}

	public GameUpdateDTO update(long gameId, double delta) {
		Game game = this.gameRepository.findOne(gameId);
		BulletHitContactListener bulletHitListener = new BulletHitContactListener(
				game);
		game.addWorldListener(bulletHitListener);
		game.update(delta);
		game.removeWorldListener(bulletHitListener);
		return new GameUpdateDTO(bulletHitListener.getUpdates(),
				bulletHitListener.getScoreUpdates());
	}

}
