package pl.letscode.tanks.map.terrain.brick;

import org.dyn4j.dynamics.Body;

import pl.letscode.tanks.engine.objects.Shootable;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainType;

import com.google.common.base.Objects;

public class BrickBlock extends TerrainObject implements Shootable {

	private int brickHp = 4;

	public BrickBlock(Body body, long id) {
		super(body, id);
		this.type = TerrainType.BRICK;
	}

	@Override
	public boolean isDead() {
		return this.brickHp < 1;
	}

	@Override
	public void shootMe() {
		this.brickHp--;
	}

	@Override
	public int getHP() {
		return this.brickHp;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id)
				.addValue(this.type).addValue(this.brickHp).toString();
	}

}
