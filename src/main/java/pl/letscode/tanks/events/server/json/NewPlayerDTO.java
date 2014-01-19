package pl.letscode.tanks.events.server.json;

public class NewPlayerDTO implements ServerData {

	private String name;

	public NewPlayerDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
