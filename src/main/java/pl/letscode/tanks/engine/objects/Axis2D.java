package pl.letscode.tanks.engine.objects;

import com.google.common.base.Objects;

public enum Axis2D {

	NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0), NONE(0, 0);

	private double x;
	private double y;

	private Axis2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.x).addValue(this.y)
				.toString();
	}

}
