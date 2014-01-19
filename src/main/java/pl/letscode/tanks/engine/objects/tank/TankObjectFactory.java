package pl.letscode.tanks.engine.objects.tank;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Mass.Type;
import org.dyn4j.geometry.Rectangle;

import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.ObjectIdGenerator;
import pl.letscode.tanks.player.Player;

import com.google.inject.Inject;

public class TankObjectFactory {

	private static final int DEFAULT_TANK_SIZE = 25;
	private static final int DEFAULT_TANK_HP = 3;
	private static final double DEFAULT_SPEED = 50;
	private static final Axis2D DEFAULT_FACING = Axis2D.SOUTH;
	private static final Axis2D DEFAULT_MOVEMENT = Axis2D.NONE;

	private final ObjectIdGenerator idGenerator;

	@Inject
	public TankObjectFactory(ObjectIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public TankObject buildTank(Player player) {
		Body tankBody = buildBody();
		TankObject tankObject = new TankObject(tankBody,
				this.idGenerator.getNextId(), player, DEFAULT_TANK_HP,
				DEFAULT_SPEED, DEFAULT_FACING, DEFAULT_MOVEMENT);
		tankBody.setUserData(tankObject);
		return tankObject;
	}

	private Body buildBody() {
		Body body = new Body();
		Rectangle rectangle = new Rectangle(DEFAULT_TANK_SIZE,
				DEFAULT_TANK_SIZE);
		BodyFixture bodyFixture = new BodyFixture(rectangle);
		body.addFixture(bodyFixture);
		body.setMass();
		body.setMassType(Type.FIXED_ANGULAR_VELOCITY);
		return body;
	}

}
