package pl.letscode.tanks.events.server.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = TankRespawnDTO.class, name = "tankRespawn"),
		@JsonSubTypes.Type(value = DirectionChangedDTO.class, name = "directionChange"),
		@JsonSubTypes.Type(value = PlayerQuitDTO.class, name = "playerQuit"),
		@JsonSubTypes.Type(value = NewPlayerDTO.class, name = "newPlayer"),
		@JsonSubTypes.Type(value = TankFireDTO.class, name = "tankFire"),
		@JsonSubTypes.Type(value = MapRespawnDTO.class, name = "mapRespawn"),
		@JsonSubTypes.Type(value = BulletHitDTO.class, name = "bulletHit"),
		@JsonSubTypes.Type(value = WorldStateDTO.class, name = "worldState"),
		@JsonSubTypes.Type(value = ScoreUpdateDTO.class, name = "scoreUpdate"),})
public interface ServerData {

}
