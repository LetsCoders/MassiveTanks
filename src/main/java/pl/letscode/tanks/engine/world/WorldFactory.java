package pl.letscode.tanks.engine.world;

import org.dyn4j.dynamics.World;

import pl.letscode.tanks.engine.objects.PhysicsObject;

public class WorldFactory {

	public World createNoGravityWorld() {
		World result = new World();
		result.setGravity(World.ZERO_GRAVITY);
		result.getSettings().setAutoSleepingEnabled(false);
		return result;
	}
	
	public World createNoGravityWorld(Iterable<PhysicsObject> objects) {
		World result = new World();
		result.setGravity(World.ZERO_GRAVITY);
		result.getSettings().setAutoSleepingEnabled(false);
		for (PhysicsObject physicsObject : objects) {
			result.addBody(physicsObject.getBody());
		}
		return result;
	}
	
}
