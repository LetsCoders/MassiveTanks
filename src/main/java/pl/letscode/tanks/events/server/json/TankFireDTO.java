package pl.letscode.tanks.events.server.json;

import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.Position;

public class TankFireDTO implements ServerData {

	private long tankId;
	private Position position;
	private long bulletId;
	private double bulletSpeed;
	private Axis2D movementDirection;
	
	public TankFireDTO(long tankId, Position position, long bulletId,
			double bulletSpeed, Axis2D movementDirection) {
		super();
		this.tankId = tankId;
		this.position = position;
		this.bulletId = bulletId;
		this.bulletSpeed = bulletSpeed;
		this.movementDirection = movementDirection;
	}
	public long getTankId() {
		return tankId;
	}
	public Position getPosition() {
		return position;
	}
	public long getBulletId() {
		return bulletId;
	}
	public double getBulletSpeed() {
		return bulletSpeed;
	}
	public Axis2D getMovementDirection() {
		return movementDirection;
	}
	
}
