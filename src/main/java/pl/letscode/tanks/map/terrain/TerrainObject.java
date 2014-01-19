package pl.letscode.tanks.map.terrain;

import org.dyn4j.dynamics.Body;

import pl.letscode.tanks.engine.objects.BaseGameObject;

/**
 * General class to represent every terrain.
 * 
 * @author Tony
 * 
 */
public class TerrainObject extends BaseGameObject {

	public static final int DEFAULT_TERRAIN_SIZE = 32;

	protected TerrainType type;

	public TerrainObject(Body body, long id) {
		super(body, id);
	}

	public TerrainType getType() {
		return type;
	}

}
