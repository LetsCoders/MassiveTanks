package pl.letscode.tanks.command;

import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.engine.GameService;

public interface GameCommand {

	public void executeCommand(GameService service, BroadcasterCommunication communication);
	
}
