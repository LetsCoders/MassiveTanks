package pl.letscode.tanks.engine.contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.letscode.tanks.engine.Game;
import pl.letscode.tanks.engine.objects.Shootable;
import pl.letscode.tanks.engine.objects.bullet.BulletObject;
import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.events.server.json.BulletHitDTO;
import pl.letscode.tanks.events.server.json.ScoreUpdateDTO;
import pl.letscode.tanks.map.terrain.brick.BrickBlock;
import pl.letscode.tanks.player.Player;

import com.google.common.collect.ImmutableList;

public class BulletHitContactListener extends
		SensorContactListener<BulletObject, Shootable> {

	private final Game game;
	private final List<BulletHitDTO> updates;
	private final List<ScoreUpdateDTO> scoreUpdates;

	public BulletHitContactListener(Game game) {
		super(BulletObject.class, Shootable.class);
		this.game = game;
		this.updates = new ArrayList<BulletHitDTO>();
		this.scoreUpdates = new ArrayList<ScoreUpdateDTO>();
	}

	public Collection<BulletHitDTO> getUpdates() {
		return ImmutableList.copyOf(this.updates);
	}

	public List<ScoreUpdateDTO> getScoreUpdates() {
		return ImmutableList.copyOf(this.scoreUpdates);
	}

	@Override
	public void onContact(final BulletObject bullet, final Shootable hitTarget) {
		game.bulletHit(bullet, hitTarget);

		this.updates.add(new BulletHitDTO(bullet.getId(), hitTarget.getId(),
				hitTarget.getHP()));

		if (hitTarget.isDead()) {
			if (hitTarget instanceof TankObject) {
				TankObject tank = (TankObject) hitTarget;
				game.removeTank(tank);
				// add one to players score
				Player shootingPlayer = bullet.getSourceTank().getPlayer();
				shootingPlayer.addFrag();
				this.scoreUpdates.add(new ScoreUpdateDTO(shootingPlayer
						.getName(), shootingPlayer.getScore()));
			} else if (hitTarget instanceof BrickBlock) {
				game.removeTerrainBlock((BrickBlock) hitTarget);
			}
		}

	}

}