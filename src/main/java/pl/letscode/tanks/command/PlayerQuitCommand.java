package pl.letscode.tanks.command;

import java.util.ArrayList;
import java.util.Collection;

import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.engine.GameService;
import pl.letscode.tanks.events.server.json.PlayerQuitDTO;
import pl.letscode.tanks.player.Player;

public class PlayerQuitCommand extends BaseClientGameCommand {

	@Override
	public void executeCommand(GameService service,
			BroadcasterCommunication communication) {
		PlayerQuitDTO dto = service.playerQuit(getGameId(), getPlayer());
		Iterable<Player> playersToInform = service.getPlayers(getGameId());
		communication.sendTo(playersToInform, dto);
	}

}
