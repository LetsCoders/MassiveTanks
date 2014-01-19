package pl.letscode.tanks.atmosphere;

import java.io.IOException;

import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.interceptor.OnDisconnectInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.atmosphere.websocket.WebSocketHandler;
import org.atmosphere.websocket.WebSocketProcessor.WebSocketException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.command.BaseClientGameCommand;
import pl.letscode.tanks.command.NewPlayerCommand;
import pl.letscode.tanks.command.PlayerQuitCommand;
import pl.letscode.tanks.engine.GameUpdater;
import pl.letscode.tanks.engine.ServerService;
import pl.letscode.tanks.guice.GuiceInjector;
import pl.letscode.tanks.player.Player;
import pl.letscode.tanks.player.PlayerFactory;
import pl.letscode.tanks.session.PlayerConnections;
import pl.letscode.tanks.session.Session;

import com.google.inject.Injector;

@WebSocketHandlerService(path = "/tanks", interceptors = {
		AtmosphereResourceLifecycleInterceptor.class,
		TrackMessageSizeInterceptor.class, HeartbeatInterceptor.class,
		SuspendTrackerInterceptor.class, OnDisconnectInterceptor.class }, broadcaster = SimpleBroadcaster.class, broadcasterCache = UUIDBroadcasterCache.class)
public class TankWebSocketHandler implements WebSocketHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TankWebSocketHandler.class);

	private static final long DEFAULT_GAME_ID = 1l;

	private Injector injector;

	private final PlayerConnections connections;
	private final Session sessions;
	private final GameUpdater updater;
	private final PlayerFactory playerFactory;
	private final ServerService serverService;

	private final ObjectMapper mapper;

	public TankWebSocketHandler() {
		super();
		injector = GuiceInjector.getInstance();
		this.connections = get(PlayerConnections.class);
		this.sessions = get(Session.class);
		this.updater = get(GameUpdater.class);
		this.playerFactory = get(PlayerFactory.class);
		this.serverService = get(ServerService.class);
		this.mapper = new ObjectMapper();

		// start with one game
		this.serverService.createNewGame(DEFAULT_GAME_ID);
		this.updater.startUpdatingGame(DEFAULT_GAME_ID);
	}

	public <T> T get(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	@Override
	public void onByteMessage(WebSocket webSocket, byte[] data, int offset,
			int length) throws IOException {
		AtmosphereResource r = webSocket.resource();
		AtmosphereRequest req = r.getRequest();
		String sessionId = req.getSession(true).getId();
		LOGGER.warn(
				"sessionId: {}, resourceId: {} : byte message should not happen",
				sessionId, r.uuid());
	}

	@Override
	public void onTextMessage(WebSocket webSocket, String data)
			throws IOException {
		AtmosphereResource r = webSocket.resource();
		AtmosphereRequest req = r.getRequest();
		String sessionId = req.getSession(true).getId();
		LOGGER.info("sessionId: {}, resourceId: {} : text message", sessionId,
				r.uuid());

		String json = data.trim();

		Player player = this.sessions.get(sessionId);
		LOGGER.info(player.toString());
		BaseClientGameCommand clientEvent = mapper.readValue(json,
				BaseClientGameCommand.class);
		clientEvent.setPlayer(player);
		clientEvent.setGameId(DEFAULT_GAME_ID);
		this.updater.issueCommand(DEFAULT_GAME_ID, clientEvent);

	}

	@Override
	public void onOpen(WebSocket webSocket) throws IOException {
		AtmosphereResource r = webSocket.resource();
		AtmosphereRequest req = r.getRequest();
		String sessionId = req.getSession(true).getId();
		LOGGER.info("sessionId: {}, resourceId: {} : websocket opened",
				sessionId, r.uuid());

		// debug, logging
		r.addEventListener(new WebSocketEventListenerImpl());
		// real listener
		r.addEventListener(new WebSocketDisconnectHandler());

		Player player = this.sessions.get(sessionId);

		// TODO: really it should just throw a runtime exception, it should not
		// happen at all, we need to check if a player exists, if not, then
		// redirect to the first page
		if (player == null) {
			LOGGER.info("New player");
			player = this.playerFactory.createRandomNamePlayer();
			this.sessions.put(sessionId, player);
		} else {
			this.connections.remove(player);
		}

		this.connections.put(player, r);

		NewPlayerCommand clientEvent = new NewPlayerCommand();
		clientEvent.setPlayer(player);
		clientEvent.setGameId(DEFAULT_GAME_ID);
		this.updater.issueCommand(DEFAULT_GAME_ID, clientEvent);
	}

	@Override
	public void onClose(WebSocket webSocket) {
		// empty, because the listener takes care of it
	}

	@Override
	public void onError(WebSocket webSocket, WebSocketException t) {
		AtmosphereResource r = webSocket.resource();
		String sessionId = r.getRequest().getSession(true).getId();
		LOGGER.error("sessionId: {}, resourceId: {} : websocket error",
				sessionId, r.uuid(), t);
	}

	private void playerExit(AtmosphereResource resource) {
		String sessionId = resource.getRequest().getSession(true).getId();
		LOGGER.info("sessionId: {}, resourceId: {} : websocket close",
				sessionId, resource.uuid());

		Player player = this.sessions.get(sessionId);

		// without this check one can crash the server using chrome and chromium
		// at the same time
		if (player != null) {

			// remove connection associated data
			this.sessions.remove(sessionId);
			this.connections.remove(player);

			// tell the game to account for the missing player
			PlayerQuitCommand quit = new PlayerQuitCommand();
			quit.setPlayer(player);
			quit.setGameId(DEFAULT_GAME_ID);
			this.updater.issueCommand(DEFAULT_GAME_ID, quit);

		}

	}

	private class WebSocketDisconnectHandler extends
			WebSocketEventListenerAdapter {

		/**
		 * This method is always invoked when tab is reloaded or closed on any
		 * browser.
		 */
		@Override
		public void onDisconnect(AtmosphereResourceEvent event) {
			playerExit(event.getResource());
		}

	}

}
