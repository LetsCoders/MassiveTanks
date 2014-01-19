package pl.letscode.tanks.map.terrain.solid;

import org.dyn4j.dynamics.Body;

import pl.letscode.tanks.engine.objects.Shootable;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainType;

import com.google.common.base.Objects;

public class SolidBlock extends TerrainObject implements Shootable {

	public SolidBlock(Body body, long id) {
		super(body, id);
		this.type = TerrainType.SOLID;
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public void shootMe() {
		// do nothing, solid block cannot be destroyed
	}

	@Override
	public int getHP() {
		return 1;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id)
				.addValue(this.type).toString();
	}

}
