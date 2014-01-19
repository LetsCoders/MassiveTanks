package pl.letscode.tanks.map;

import com.google.common.base.Objects;

/**
 * Coord used to locate terrain brick in {@link MapMatrix}
 * 
 * @author Tony
 * 
 */
public class Coord {

	private int x;
	private int y;

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	/* Utilities */
	
	public int hashCode() {
		return Objects.hashCode(this.x, this.y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Coord))
			return false;
		Coord that = (Coord) obj;
		if (this.x == that.x && this.y == that.y)
			return true;
		return false;
	}

}
