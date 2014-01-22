package pl.letscode.tanks.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.engine.objects.Position;
import pl.letscode.tanks.map.terrain.RandomTerrainProvider;
import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainObjectFactory;
import pl.letscode.tanks.map.terrain.TerrainType;

import com.google.inject.Inject;

public class MapFactory {

	private final static Logger LOG = LoggerFactory.getLogger(MapFactory.class);

	private final TerrainObjectFactory objectFactory;
	private final RandomTerrainProvider randomTerrainProvider;

	@Inject
	public MapFactory(TerrainObjectFactory objectFactory,
			RandomTerrainProvider randomTerrainProvider) {
		this.objectFactory = objectFactory;
		this.randomTerrainProvider = randomTerrainProvider;
	}

	public MapMatrix createEmptyMap(int height, int width) {
		return new MapMatrix(height, width);
	}

	public MapMatrix createRandomMap(int height, int width) {
		MapMatrix result = new MapMatrix(height, width);
		this.fillBorders(result);
		this.fillWithRandomBlocks(result);
		String log = "";
		for (int i = 0; i < result.getMatrix().length; i++) {
			for (int j = 0; j < result.getMatrix()[i].length; j++) {
				TerrainObject terrainObject = result.getMatrix()[i][j];
				if (terrainObject == null) {
					log.concat("0,");
				} else {
					log.concat(terrainObject.getType().ordinal() + ",");
				}
			}
			log.concat("/n");
		}
		LOG.info(log);
		return result;
	}

	private void fillBorders(MapMatrix mapMatrix) {
		final TerrainObject[][] matrix = mapMatrix.getMatrix();
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][0] = objectFactory.buildTerrainBlock(TerrainType.SOLID);
			matrix[i][0].setPosition(new Position(i * 32, 0));
			matrix[i][mapMatrix.getHeight() + 1] = objectFactory
					.buildTerrainBlock(TerrainType.SOLID);
			matrix[i][mapMatrix.getHeight() + 1].setPosition(new Position(
					i * 32, (mapMatrix.getHeight() + 1) * 32));
		}
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[0][i] = objectFactory.buildTerrainBlock(TerrainType.SOLID);
			matrix[0][i].setPosition(new Position(0, i * 32));
			matrix[mapMatrix.getWidth() + 1][i] = objectFactory
					.buildTerrainBlock(TerrainType.SOLID);
			matrix[mapMatrix.getWidth() + 1][i].setPosition(new Position(
					(mapMatrix.getWidth() + 1) * 32, i * 32));
		}
	}

	private void fillWithRandomBlocks(MapMatrix mapMatrix) {
		for (int i = 1; i <= mapMatrix.getHeight(); i++) {
			for (int j = 1; j <= mapMatrix.getWidth(); j++) {
				mapMatrix.setTerrainObject(new Coord(i, j),
						this.randomTerrainProvider.getRandom());
				if (mapMatrix.exists(i, j))
					mapMatrix.getMatrix()[i][j].setPosition(new Position(
							i * 32, j * 32));
			}
		}
	}

}
