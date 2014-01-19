package pl.letscode.tanks.command;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.engine.GameService;
import pl.letscode.tanks.events.server.json.NewPlayerDTO;
import pl.letscode.tanks.events.server.json.WorldStateDTO;
import pl.letscode.tanks.player.Player;

public class NewPlayerCommand extends BaseClientGameCommand {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NewPlayerCommand.class);
	
	@Override
	public void executeCommand(GameService service,
			BroadcasterCommunication communication) {
		LOGGER.info("Executing new player command");
		// execute
		WorldStateDTO dto = service.addNewPlayer(getGameId(), getPlayer());
		// communicate
		communication.sendTo(getPlayer(), dto);
		Iterable<Player> playersToInform = service.getPlayers(getGameId());
		Collection<Player> otherPlayers = new ArrayList<Player>();
		for (Player player : playersToInform) {
			if (!player.equals(getPlayer()))
				otherPlayers.add(player);
		}
		NewPlayerDTO newPlayerDto = new NewPlayerDTO(getPlayer().getName());
		communication.sendTo(otherPlayers, newPlayerDto);
	}

}
