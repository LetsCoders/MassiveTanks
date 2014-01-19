package pl.letscode.tanks.command;

import pl.letscode.tanks.communication.BroadcasterCommunication;
import pl.letscode.tanks.engine.GameService;
import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.events.server.json.DirectionChangedDTO;
import pl.letscode.tanks.player.Player;

public class TankDirectionChangeCommand extends BaseClientGameCommand {

	private Axis2D direction;
	
	public Axis2D getDirection() {
		return this.direction;
	}
	
	public void setDirection(Axis2D direction) {
		this.direction = direction;
	}

	@Override
	public void executeCommand(GameService service,
			BroadcasterCommunication communication) {
		DirectionChangedDTO dto = service.changeTankDirection(getGameId(), getPlayer(), this.direction);
		if (dto != null) {
			Iterable<Player> playersToInform = service.getPlayers(getGameId());
			communication.sendTo(playersToInform, dto);
		}
	}

}
