package pl.letscode.tanks.communication;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.events.server.json.ServerData;
import pl.letscode.tanks.player.Player;
import pl.letscode.tanks.player.PlayerRepository;
import pl.letscode.tanks.session.PlayerConnections;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BroadcasterCommunication {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BroadcasterCommunication.class);

	private final PlayerRepository playerRepository;
	private final PlayerConnections connections;
	private final ObjectMapper mapper;

	@Inject
	public BroadcasterCommunication(PlayerRepository register,
			PlayerConnections connections) {
		this.playerRepository = register;
		this.connections = connections;
		this.mapper = new ObjectMapper();
	}
	
	public void sendTo(Player player, ServerData data) {
		String json = toJson(data);
		connections.get(player).getResponse().write(json);
	}
	
	public void sendTo(Iterable<Player> players, ServerData data) {
		String json = toJson(data);
		for (Player player : players) {
			connections.get(player).getResponse().write(json);
		}
	}

	private String toJson(ServerData data) {
		try {
			LOGGER.info(mapper.writeValueAsString(data));
			return mapper.writeValueAsString(data);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cant create JSON of object" + e);
		}
	}

}
