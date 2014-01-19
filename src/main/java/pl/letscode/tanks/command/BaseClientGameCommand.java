package pl.letscode.tanks.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pl.letscode.tanks.player.Player;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value=TankRespawnCommand.class, name="respawn"),
    @JsonSubTypes.Type(value=TankDirectionChangeCommand.class, name="directionChange"),
    @JsonSubTypes.Type(value=PlayerQuitCommand.class, name="playerQuit"),
    @JsonSubTypes.Type(value=NewPlayerCommand.class, name="newPlayer"),
    @JsonSubTypes.Type(value=TankFireCommand.class, name="tankFire"),
})
public abstract class BaseClientGameCommand implements GameCommand {

	private long gameId;
	private Player player;
	
	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
