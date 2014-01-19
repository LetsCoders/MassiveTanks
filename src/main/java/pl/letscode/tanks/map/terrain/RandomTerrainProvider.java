package pl.letscode.tanks.map.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.inject.Inject;

/**
 * Realy simple random terrain provider. Each block have 10% to spawn and there
 * is 50% that nothing will be spawned.
 * 
 * @author Tony
 */
public class RandomTerrainProvider {

	private static final long serialVersionUID = 1632167996104643808L;

	private final TerrainObjectFactory objectFactory;
	private final List<TerrainType> terrainTypes;
	private final Random random;

	@Inject
	public RandomTerrainProvider(TerrainObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
		this.random = new Random();
		this.terrainTypes = this.fillPool();
	}

	public TerrainObject getRandom() {
		int randomNumber = random.nextInt(10);
		TerrainType type = this.terrainTypes.get(randomNumber);
		return objectFactory.buildTerrainBlock(type);
	}

	private List<TerrainType> fillPool() {
		List<TerrainType> result = new ArrayList<TerrainType>();
		result.add(TerrainType.SAND);
		result.add(TerrainType.WATER);
		result.add(TerrainType.GRASS);
		result.add(TerrainType.BRICK);
		result.add(TerrainType.SOLID);
		result.add(null);
		result.add(null);
		result.add(null);
		result.add(null);
		result.add(null);
		return result;
	}
}
