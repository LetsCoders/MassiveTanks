package pl.letscode.tanks.map.terrain.brick;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Mass.Type;
import org.dyn4j.geometry.Rectangle;

import pl.letscode.tanks.map.terrain.TerrainObject;

public final class BrickBlockFactory {

	private BrickBlockFactory() {
		// private
	}

	public static BrickBlock getBlock(final long id) {
		Body brickBody = buildBody();
		BrickBlock brickBlock = new BrickBlock(brickBody, id);
		brickBody.setUserData(brickBlock);
		return brickBlock;
	}

	private static Body buildBody() {
		Body body = new Body();
		Rectangle rectangle = new Rectangle(TerrainObject.DEFAULT_TERRAIN_SIZE,
				TerrainObject.DEFAULT_TERRAIN_SIZE);
		BodyFixture bodyFixture = new BodyFixture(rectangle);
		bodyFixture.setRestitution(0.01);
		body.addFixture(bodyFixture);
		body.setMass();
		body.setMassType(Type.INFINITE);
		return body;
	}

}
