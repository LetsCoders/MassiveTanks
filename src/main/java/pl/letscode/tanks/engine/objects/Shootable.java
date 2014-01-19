package pl.letscode.tanks.engine.objects;

public interface Shootable extends PhysicsObject {

	boolean isDead();

	void shootMe();

	int getHP();

}
