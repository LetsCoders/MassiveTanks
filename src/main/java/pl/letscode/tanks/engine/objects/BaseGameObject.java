package pl.letscode.tanks.engine.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;

public abstract class BaseGameObject implements PhysicsObject {

	@JsonIgnore
	protected Body body;
	protected long id;

	public BaseGameObject(Body body, long id) {
		this.body = body;
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@JsonIgnore
	public Body getBody() {
		return body;
	}

	@Override
	public void setPosition(final Position position) {
		this.body.getTransform().setTranslation(position.getX(),
				position.getY());
	}

	@Override
	public Position getPosition() {
		Transform transform = this.body.getTransform();
		return new Position(transform.getTranslationX(),
				transform.getTranslationY());
	}

}
