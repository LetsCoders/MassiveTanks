package pl.letscode.tanks.map.terrain;

import pl.letscode.tanks.engine.objects.ObjectIdGenerator;
import pl.letscode.tanks.map.terrain.brick.BrickBlockFactory;
import pl.letscode.tanks.map.terrain.grass.GrassBrickFactory;
import pl.letscode.tanks.map.terrain.sand.SandBlockFactory;
import pl.letscode.tanks.map.terrain.solid.SolidBlockFactory;
import pl.letscode.tanks.map.terrain.water.WaterBlockFactory;

import com.google.inject.Inject;

public class TerrainObjectFactory {

	@Inject
	private ObjectIdGenerator idGenerator;

	public TerrainObject buildTerrainBlock(final TerrainType type) {
		if (type == null) {
			return null;
		}
		switch (type) {
		case SOLID:
			return SolidBlockFactory.getBlock(idGenerator.getNextId());
		case BRICK:
			return BrickBlockFactory.getBlock(idGenerator.getNextId());
		case WATER:
			return WaterBlockFactory.getBlock(idGenerator.getNextId());
		case SAND:
			return SandBlockFactory.getBlock(idGenerator.getNextId());
		case GRASS:
			return GrassBrickFactory.getBlock(idGenerator.getNextId());
		default:
			return null;
		}
	}

}
