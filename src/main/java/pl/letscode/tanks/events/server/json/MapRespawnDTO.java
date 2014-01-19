package pl.letscode.tanks.events.server.json;

import pl.letscode.tanks.map.terrain.TerrainObject;

public class MapRespawnDTO implements ServerData {

	private TerrainObject block;
	
	public MapRespawnDTO(TerrainObject block) {
		this.block = block;
	}

	public TerrainObject getBlock() {
		return block;
	}
	
}
