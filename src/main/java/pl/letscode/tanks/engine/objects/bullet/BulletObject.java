package pl.letscode.tanks.engine.objects.bullet;

import org.dyn4j.dynamics.Body;

import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.BaseGameObject;
import pl.letscode.tanks.engine.objects.tank.TankObject;

import com.google.common.base.Objects;

public class BulletObject extends BaseGameObject {

	private TankObject sourceTank;
	private Axis2D direction;
	private int damage;
	private double movementSpeed;

	public BulletObject(Body body, long id, TankObject sourceTank,
			Axis2D direction, int damage, double movementSpeed) {
		super(body, id);
		this.sourceTank = sourceTank;
		this.direction = direction;
		this.damage = damage;
		this.movementSpeed = movementSpeed;
	}

	public int getDamage() {
		return damage;
	}

	public Axis2D getDirection() {
		return direction;
	}

	public TankObject getSourceTank() {
		return sourceTank;
	}

	public double getMovementSpeed() {
		return this.movementSpeed;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof BulletObject))
			return false;
		BulletObject that = (BulletObject) obj;
		if (this.id == that.id)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id)
				.addValue(this.getPosition()).addValue(this.sourceTank)
				.addValue(this.direction).addValue(this.damage)
				.addValue(this.movementSpeed).toString();
	}

}
