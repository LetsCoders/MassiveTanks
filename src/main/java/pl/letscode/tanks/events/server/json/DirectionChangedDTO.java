package pl.letscode.tanks.events.server.json;

import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.Position;

public class DirectionChangedDTO implements ServerData {

	private long tankId;
	private Axis2D movementDirection;
	private Axis2D facingDirection;
	private Position position;
	
	public DirectionChangedDTO(long tankId, Axis2D movementDirection,
			Axis2D facingDirection, Position position) {
		super();
		this.tankId = tankId;
		this.movementDirection = movementDirection;
		this.facingDirection = facingDirection;
		this.position = position;
	}
	
	public long getTankId() {
		return tankId;
	}
	public Axis2D getMovementDirection() {
		return movementDirection;
	}
	public Axis2D getFacingDirection() {
		return facingDirection;
	}
	public Position getPosition() {
		return position;
	}
	
	
}
