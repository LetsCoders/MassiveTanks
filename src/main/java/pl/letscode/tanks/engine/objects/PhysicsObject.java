package pl.letscode.tanks.engine.objects;

import org.dyn4j.dynamics.Body;

public interface PhysicsObject {

	long getId();

	Body getBody();

	void setPosition(Position position);

	Position getPosition();

}
