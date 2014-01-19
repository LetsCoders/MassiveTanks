package pl.letscode.tanks.engine;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.letscode.tanks.command.GameCommand;
import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.events.server.json.BulletHitDTO;
import pl.letscode.tanks.events.server.json.GameUpdateDTO;
import pl.letscode.tanks.events.server.json.MapRespawnDTO;
import pl.letscode.tanks.player.Player;

/**
 * It needs to be a singleton. It holds state and so it is an application scoped
 * component. Right now it isn't multithreaded but can be changed easily.
 * 
 * @author edhendil
 * 
 */
@Singleton
public class GameUpdater {

	private final GameService gameService;
	private final BroadcasterCommunication communication;

	// commands to be executed
	private final ConcurrentLinkedQueue<GameCommand> commands;

	// for issuing recurring tasks
	private final ScheduledExecutorService executor;

	@Inject
	public GameUpdater(GameService gameService,
			BroadcasterCommunication communication) {
		super();
		this.gameService = gameService;
		this.communication = communication;
		this.commands = new ConcurrentLinkedQueue<GameCommand>();
		this.executor = Executors.newScheduledThreadPool(1);
	}

	/**
	 * 
	 * @param gameId
	 *            - ignored for now
	 * @param command
	 */
	public void issueCommand(long gameId, GameCommand command) {
		this.commands.add(command);
	}

	public void startUpdatingGame(long gameId) {
		long timeNow = System.currentTimeMillis();
		this.executor.scheduleAtFixedRate(new FrameUpdate(timeNow, gameId,
				this.commands, this.gameService, this.communication), 0, 10,
				TimeUnit.MILLISECONDS);
		this.commands.add(new MapRespawnCommand(gameId, 10000));
	}

	private class FrameUpdate implements Runnable {

		private long lastTimestamp;
		private final long gameId;
		private final ConcurrentLinkedQueue<GameCommand> commands;
		private final GameService gameService;
		private final BroadcasterCommunication communication;

		public FrameUpdate(long lastTimestamp, long gameId,
				ConcurrentLinkedQueue<GameCommand> commands,
				GameService gameService, BroadcasterCommunication communication) {
			super();
			this.lastTimestamp = lastTimestamp;
			this.gameId = gameId;
			this.commands = commands;
			this.gameService = gameService;
			this.communication = communication;
		}

		@Override
		public void run() {
			long timeNow = System.currentTimeMillis();
			double delta = (timeNow - this.lastTimestamp) / 1000.0;
			this.lastTimestamp = System.currentTimeMillis();
			GameCommand command = this.commands.poll();
			while (command != null) {
				command.executeCommand(this.gameService, this.communication);
				command = this.commands.poll();
			}
			GameCommand gameUpdate = new GameUpdateCommand(this.gameId, delta);
			gameUpdate.executeCommand(this.gameService, this.communication);
		}

	}

	private class GameUpdateCommand implements GameCommand {

		private final long gameId;
		private final double delta;

		public GameUpdateCommand(long gameId, double delta) {
			this.gameId = gameId;
			this.delta = delta;
		}

		@Override
		public void executeCommand(GameService service,
				BroadcasterCommunication communication) {
			// execute
			GameUpdateDTO dto = service.update(gameId, this.delta);

			Iterable<Player> playersToInform = service.getPlayers(gameId);

			// inform clients
			for (BulletHitDTO bulletHit : dto.getBulletHits())
				communication.sendTo(playersToInform, bulletHit);
		}

	}

	private class MapRespawnCommand implements GameCommand {

		private final long gameId;
		private final long milisecondsDelay;

		public MapRespawnCommand(long gameId, long milisecondsDelay) {
			this.gameId = gameId;
			this.milisecondsDelay = milisecondsDelay;
		}

		@Override
		public void executeCommand(GameService service,
				BroadcasterCommunication communication) {

			// execute
			MapRespawnDTO dto = service.regenerateMap(this.gameId);

			// communicate
			if (dto != null) {
				Iterable<Player> playersToInform = service.getPlayers(gameId);
				communication.sendTo(playersToInform, dto);
			}

			// schedule
			GameUpdater.this.executor.schedule(new Runnable() {

				@Override
				public void run() {
					GameUpdater.this.commands.add(new MapRespawnCommand(gameId,
							milisecondsDelay));
				}

			}, this.milisecondsDelay, TimeUnit.MILLISECONDS);

		}

	}

}
