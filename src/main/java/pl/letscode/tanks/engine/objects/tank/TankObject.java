package pl.letscode.tanks.engine.objects.tank;

import org.dyn4j.dynamics.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.BaseGameObject;
import pl.letscode.tanks.engine.objects.Position;
import pl.letscode.tanks.engine.objects.Shootable;
import pl.letscode.tanks.player.Player;

import com.google.common.base.Objects;

public class TankObject extends BaseGameObject implements Shootable {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TankObject.class);

	private final Player player;
	private Axis2D facingDirection;
	private Axis2D movementDirection;
	private int hp;
	private double movementSpeed;

	public TankObject(Body body, long id, Player player, int initialHp,
			double movementSpeed, Axis2D facingDirection,
			Axis2D movementDirection) {
		super(body, id);
		this.hp = initialHp;
		this.movementSpeed = movementSpeed;
		this.facingDirection = facingDirection;
		this.movementDirection = movementDirection;
		this.player = player;
	}

	public Axis2D getFacingDirection() {
		return facingDirection;
	}

	@Override
	public boolean isDead() {
		return this.hp < 1;
	}

	@Override
	public void shootMe() {
		this.hp--;
	}

	@Override
	public int getHP() {
		return this.hp;
	}

	public Axis2D getMovementDirection() {
		return this.movementDirection;
	}

	public Position getVelocity() {
		return new Position(this.getBody().getLinearVelocity().x, this
				.getBody().getLinearVelocity().y);
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public boolean changeTankDirection(Axis2D newDirection) {
		if (this.movementDirection.equals(newDirection)) {
			return false;
		} else {
			this.movementDirection = newDirection;
			if (!newDirection.equals(Axis2D.NONE)) {
				this.facingDirection = newDirection;
			}
			LOGGER.info("Direction {}", newDirection);
			LOGGER.info("New speed x, y {}, {}", newDirection.getX()
					* movementSpeed, newDirection.getY() * movementSpeed);
			// set speed
			this.getBody().setLinearVelocity(
					newDirection.getX() * movementSpeed,
					newDirection.getY() * movementSpeed);
			this.getBody().setAsleep(false);
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof TankObject))
			return false;
		TankObject that = (TankObject) obj;
		if (this.id == that.id)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id).addValue(this.hp)
				.addValue(this.movementSpeed).addValue(this.movementDirection)
				.addValue(this.facingDirection).toString();
	}

}
