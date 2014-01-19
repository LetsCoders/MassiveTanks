package pl.letscode.tanks.events.server.json;

import pl.letscode.tanks.engine.objects.tank.TankObject;

public class TankRespawnDTO implements ServerData {

	private String playerName;
	private TankObject tank;
	
	public TankRespawnDTO(String playerName, TankObject tank) {
		super();
		this.playerName = playerName;
		this.tank = tank;
	}
	public String getPlayerName() {
		return playerName;
	}
	public TankObject getTank() {
		return tank;
	}
	
}
