package pl.letscode.tanks.events.server.json;

public class PlayerQuitDTO implements ServerData {

	private String name;
	private long tankId;
	
	public PlayerQuitDTO(String name, long tankId) {
		super();
		this.name = name;
		this.tankId = tankId;
	}
	public String getName() {
		return name;
	}
	public long getTankId() {
		return tankId;
	}

}
