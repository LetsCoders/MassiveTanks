package pl.letscode.tanks.map.terrain.sand;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Mass.Type;
import org.dyn4j.geometry.Rectangle;

import pl.letscode.tanks.map.terrain.TerrainObject;

public final class SandBlockFactory {

	private SandBlockFactory() {
		// private
	}

	public static SandBlock getBlock(final long id) {
		Body sandBody = buildBody();
		SandBlock sandBlock = new SandBlock(sandBody, id);
		sandBody.setUserData(sandBody);
		return sandBlock;
	}

	private static Body buildBody() {
		Body body = new Body();
		Rectangle rectangle = new Rectangle(TerrainObject.DEFAULT_TERRAIN_SIZE,
				TerrainObject.DEFAULT_TERRAIN_SIZE);
		BodyFixture bodyFixture = new BodyFixture(rectangle);
		bodyFixture.setRestitution(0.01);
		bodyFixture.setSensor(true);
		body.addFixture(bodyFixture);
		body.setMass();
		body.setMassType(Type.INFINITE);
		return body;
	}

}
