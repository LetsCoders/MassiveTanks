package pl.letscode.tanks.events.server.json;

import java.util.List;

import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.map.MapMatrix;

import com.google.common.collect.ImmutableList;

public class WorldStateDTO implements ServerData {

	private List<PlayerStateDTO> players;
	private PlayerStateDTO thisPlayer;
	private Iterable<BulletObject> bullets;
	private MapMatrix map;

	public WorldStateDTO(List<PlayerStateDTO> players,
			PlayerStateDTO thisPlayer, Iterable<BulletObject> bullets,
			MapMatrix map) {
		super();
		this.players = players;
		this.thisPlayer = thisPlayer;
		this.bullets = bullets;
		this.map = map;
	}

	public List<PlayerStateDTO> getPlayers() {
		return players;
	}

	public PlayerStateDTO getThisPlayer() {
		return thisPlayer;
	}

	public Iterable<BulletObject> getBullets() {
		return this.bullets;
	}

	public MapMatrix getMap() {
		return map;
	}

}
