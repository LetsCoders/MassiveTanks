package pl.letscode.tanks.command;

import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.engine.GameService;
import pl.letscode.tanks.events.server.json.TankFireDTO;
import pl.letscode.tanks.player.Player;

public class TankFireCommand extends BaseClientGameCommand {

	@Override
	public void executeCommand(GameService service,
			BroadcasterCommunication communication) {
		TankFireDTO dto = service.fireTankCannon(getGameId(), getPlayer());
		if (dto != null) {
			Iterable<Player> playersToInform = service.getPlayers(getGameId());
			communication.sendTo(playersToInform, dto);
		}
	}

}
