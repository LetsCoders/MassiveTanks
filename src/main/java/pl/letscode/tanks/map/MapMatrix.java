package pl.letscode.tanks.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.letscode.tanks.map.terrain.TerrainObject;
import pl.letscode.tanks.map.terrain.TerrainType;

import com.google.common.collect.ImmutableList;

public class MapMatrix {

	private int height;

	private int width;

	private TerrainObject[][] matrix;

	/* Constructors */
	
	MapMatrix(int height, int width) {
		this.height = height;
		this.width = width;
		// +2 for Borders
		this.matrix = new TerrainObject[height + 2][width + 2];
	}

	/* Read */
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public Collection<TerrainObject> getBlocks() {
		List<TerrainObject> result = new ArrayList<TerrainObject>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				TerrainObject terrainObject = matrix[i][j];
				if (terrainObject != null) {
					result.add(terrainObject);
				}
			}
		}
		return ImmutableList.copyOf(result);
	}

	public TerrainObject[][] getMatrix() {
		return matrix;
	}

	public TerrainObject getTerrainObject(final Coord coord) {
		return this.matrix[coord.getX()][coord.getY()];
	}
	
	public TerrainObject getTerrainObject(int x, int y) {
		return this.matrix[x][y];
	}
	
	public boolean exists(int x, int y) {
		return this.matrix[x][y] != null;
	}
	
	public int getBrickAmount() {
		int counter = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				TerrainObject terrainObject = matrix[i][j];
				if (terrainObject != null
						&& terrainObject.getType() == TerrainType.BRICK) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	/* Commands */

	public void setTerrainObject(final Coord coord,
			final TerrainObject terrainObject) {
		this.matrix[coord.getX()][coord.getY()] = terrainObject;
	}
	
	public void setTerrainObject(int x, int y,
			final TerrainObject terrainObject) {
		this.matrix[x][y] = terrainObject;
	}

	public void remove(final long id) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				TerrainObject terrainObject = matrix[i][j];
				if (terrainObject != null && terrainObject.getId() == id) {
					matrix[i][j] = null;
					return;
				}
			}
		}
	}

}
