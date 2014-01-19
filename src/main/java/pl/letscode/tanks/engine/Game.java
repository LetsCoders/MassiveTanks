package pl.letscode.tanks.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.dyn4j.Listener;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;

import pl.letscode.tanks.engine.model.GameModel;
import pl.letscode.tanks.engine.objects.PhysicsObject;
import pl.letscode.tanks.engine.objects.Shootable;
import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.events.server.json.PlayerStateDTO;
import pl.letscode.tanks.map.MapMatrix;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.player.Player;

/**
 * Aggregate root, contains whole state. World object also has to be here as it
 * contains references to Body objects. 
 * 
 * Need to somehow keep them in sync. 
 * 
 * @author edhendil
 * 
 */
public class Game {

	private final long id;
	private final GameModel model;
	private final World world;

	/* Constructors */

	Game(long id, GameModel model, World world) {
		this.id = id;
		this.model = model;
		this.world = world;
	}

	/* Read model */

	public long getId() {
		return this.id;
	}

	public Collection<Player> getPlayers() {
		return this.model.getPlayers();
	}

	public Collection<TankObject> getTanks() {
		return this.model.getTanks();
	}

	public TankObject getTankByPlayer(Player player) {
		for (TankObject tank : this.model.getTanks()) {
			if (tank.getPlayer().equals(player))
				return tank;
		}
		return null;
	}

	public Collection<BulletObject> getBullets() {
		return this.model.getBullets();
	}

	public MapMatrix getBoard() {
		return this.model.getBoard();
	}

	public boolean willCollide(PhysicsObject object) {
		for (BodyFixture fix : object.getBody().getFixtures()) {
			if (world.detect(fix.getShape(), object.getBody().getTransform())
					.size() > 0) {
				return true;
			}
		}
		return false;
	}

	public List<PlayerStateDTO> getPlayerStates() {
		List<PlayerStateDTO> data = new ArrayList<PlayerStateDTO>();
		for (Player player : this.getPlayers()) {
			data.add(new PlayerStateDTO(player, getTankByPlayer(player)));
		}
		return data;
	}

	/* Commands */

	public void addTank(TankObject tank) {
		this.model.addTank(tank);
		this.world.addBody(tank.getBody());
		this.world.setUpdateRequired(true);
	}

	public void removeTank(TankObject tank) {
		this.model.removeTank(tank);
		this.world.removeBody(tank.getBody());
		this.world.setUpdateRequired(true);
	}

	public void addBullet(BulletObject bullet) {
		this.model.addBullet(bullet);
		this.world.addBody(bullet.getBody());
		this.world.setUpdateRequired(true);
	}

	public void removeBullet(BulletObject bullet) {
		this.model.removeBullet(bullet);
		this.world.removeBody(bullet.getBody());
		this.world.setUpdateRequired(true);
	}

	public void addPlayer(Player player) {
		this.model.addPlayer(player);
	}

	public void removePlayer(Player player) {
		this.model.removePlayer(player);
	}

	public void addTerrainBlock(TerrainObject object, int x, int y) {
		this.model.addTerrainBlock(object, x, y);
		this.world.addBody(object.getBody());
		this.world.setUpdateRequired(true);
	}
	
	public void removeTerrainBlock(TerrainObject object) {
		this.model.removeTerrainBlock(object);
		this.world.removeBody(object.getBody());
		this.world.setUpdateRequired(true);
	}

	public void update(double delta) {
		this.world.updatev(delta);
	}

	public void addWorldListener(Listener listener) {
		this.world.addListener(listener);
	}

	public void removeWorldListener(Listener listener) {
		this.world.removeListener(listener);
	}
	
	public void bulletHit(BulletObject bullet, Shootable hitTarget) {
		hitTarget.shootMe();
		removeBullet(bullet);
	}

	/* Utilities */

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Game))
			return false;
		Game that = (Game) obj;
		if (this.id == that.id)
			return true;
		return false;
	}

}
