package pl.letscode.tanks.command;

import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.engine.GameService;
import pl.letscode.tanks.events.server.json.TankRespawnDTO;
import pl.letscode.tanks.player.Player;

public class TankRespawnCommand extends BaseClientGameCommand {

	@Override
	public void executeCommand(GameService service,
			BroadcasterCommunication communication) {
		TankRespawnDTO dto = service.respawnTank(getGameId(), getPlayer());
		if (dto != null) {
			Iterable<Player> playersToInform = service.getPlayers(getGameId());
			communication.sendTo(playersToInform, dto);
		}
	}

}
