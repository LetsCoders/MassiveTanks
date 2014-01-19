package pl.letscode.tanks.map.terrain.sand;

import org.dyn4j.dynamics.Body;

import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainType;

import com.google.common.base.Objects;

public class SandBlock extends TerrainObject {

	public SandBlock(Body body, long id) {
		super(body, id);
		this.type = TerrainType.SAND;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id)
				.addValue(this.type).toString();
	}

}
