package pl.letscode.tanks.map.terrain.water;

import org.dyn4j.dynamics.Body;

import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainType;

import com.google.common.base.Objects;

public class WaterBlock extends TerrainObject {

	public WaterBlock(Body body, long id) {
		super(body, id);
		this.type = TerrainType.WATER;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id)
				.addValue(this.type).toString();
	}

}
