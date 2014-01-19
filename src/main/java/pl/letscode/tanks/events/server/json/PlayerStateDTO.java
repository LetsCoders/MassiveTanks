package pl.letscode.tanks.events.server.json;

import pl.letscode.tanks.engine.objects.tank.TankObject;
import pl.letscode.tanks.player.Player;

public class PlayerStateDTO {

	private Player player;
	private TankObject tank;
	
	public PlayerStateDTO(Player player, TankObject tank) {
		super();
		this.player = player;
		this.tank = tank;
	}
	public Player getPlayer() {
		return player;
	}
	public TankObject getTank() {
		return tank;
	}
	
}
