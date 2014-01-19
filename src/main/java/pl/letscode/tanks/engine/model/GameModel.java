package pl.letscode.tanks.engine.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.map.MapMatrix;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.player.Player;

import com.google.common.collect.ImmutableList;

public class GameModel {

	private final List<Player> players;
	private final List<TankObject> tanks;
	private final List<BulletObject> bullets;
	private final MapMatrix map;
	
	/* Constructors */
	
	GameModel(List<Player> players, List<TankObject> tanks,
			List<BulletObject> bullets, MapMatrix map) {
		this.players = players;
		this.tanks = tanks;
		this.bullets = bullets;
		this.map = map;
	}
	
	/* Read model */
	
	public Collection<Player> getPlayers() {
		return ImmutableList.copyOf(this.players);
	}
	
	public Collection<TankObject> getTanks() {
		return ImmutableList.copyOf(this.tanks);
	}
	
	public Collection<BulletObject> getBullets() {
		return ImmutableList.copyOf(this.bullets);
	}
	
	public MapMatrix getBoard() {
		return this.map;
	}
	
	/* Commands */
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	public void addTank(TankObject tank) {
		this.tanks.add(tank);
	}
	
	public void removeTank(TankObject tank) {
		this.tanks.remove(tank);
	}
	
	public void addBullet(BulletObject bullet) {
		this.bullets.add(bullet);
	}
	
	public void removeBullet(BulletObject bullet) {
		this.bullets.remove(bullet);
	}

	public void addTerrainBlock(TerrainObject object, int x, int y) {
		this.map.setTerrainObject(x, y, object);
	}
	
	public void removeTerrainBlock(TerrainObject object) {
		this.map.remove(object.getId());
	}
	
}
