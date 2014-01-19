package pl.letscode.tanks.engine.model;

import java.util.ArrayList;

import javax.inject.Inject;

import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.map.MapFactory;
import pl.letscode.tanks.player.Player;

public class GameModelFactory {
	
	private final MapFactory mapFactory;
	
	@Inject
	public GameModelFactory(MapFactory mapFactory) {
		this.mapFactory = mapFactory;
	}

	public GameModel createEmpty(int mapHeight, int mapWidth) {
		return new GameModel(new ArrayList<Player>(),
				new ArrayList<TankObject>(), new ArrayList<BulletObject>(),
				this.mapFactory.createEmptyMap(mapHeight, mapWidth));
	}
	
	public GameModel createRandomMap(int mapHeight, int mapWidth) {
		return new GameModel(new ArrayList<Player>(),
				new ArrayList<TankObject>(), new ArrayList<BulletObject>(),
				this.mapFactory.createRandomMap(mapHeight, mapWidth));
	}

}
