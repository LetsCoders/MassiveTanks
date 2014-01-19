package pl.letscode.tanks.engine.objects;

import com.google.common.base.Objects;

/**
 * Position used to localize object in {@link PhysicsEngine}
 * 
 * @author Tony
 * 
 */
public class Position {

	private double x;

	private double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.x, this.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Position))
			return false;
		Position that = (Position) obj;
		if (this.x == that.x && this.y == that.y)
			return true;
		return false;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("x", this.x).add("y", this.y)
				.toString();
	}

}
