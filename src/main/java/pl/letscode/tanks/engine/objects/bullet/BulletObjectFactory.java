package pl.letscode.tanks.engine.objects.bullet;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.letscode.tanks.engine.objects.Axis2D;
import pl.letscode.tanks.engine.objects.ObjectIdGenerator;
import pl.letscode.tanks.engine.objects.Position;
import pl.letscode.tanks.engine.objects.tank.TankObject;

import com.google.inject.Inject;

public class BulletObjectFactory {

	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulletObjectFactory.class);
	
	private static final double DEFAULT_OFFSET = 20;

	private static final int DEFAULT_DAMAGE = 1;

	private static final int DEFAULT_BULLET_SPEED = 100;

	private final ObjectIdGenerator idGenerator;

	@Inject
	public BulletObjectFactory(ObjectIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public BulletObject buildBullet(final TankObject shootingTank) {
		Body bulletBody = buildBody(shootingTank);
		BulletObject bulletObject = new BulletObject(bulletBody,
				this.idGenerator.getNextId(), shootingTank,
				shootingTank.getFacingDirection(), DEFAULT_DAMAGE, DEFAULT_BULLET_SPEED);
		bulletBody.setUserData(bulletObject);
		return bulletObject;
	}

	private Body buildBody(final TankObject shootingTank) {
		Body body = new Body();
		Axis2D facingDirection = shootingTank.getFacingDirection();
		Position tankPosition = shootingTank.getPosition();
		Circle circle = new Circle(1);
		BodyFixture bodyFixture = new BodyFixture(circle);
		bodyFixture.setSensor(true);
		LOGGER.info("velocity x,y {}.{}", facingDirection.getX() * DEFAULT_BULLET_SPEED, facingDirection.getY() * DEFAULT_BULLET_SPEED);
		body.getTransform().setTranslation(
				tankPosition.getX() + facingDirection.getX() * DEFAULT_OFFSET,
				tankPosition.getY() + facingDirection.getY() * DEFAULT_OFFSET);
		body.setLinearVelocity(facingDirection.getX() * DEFAULT_BULLET_SPEED,
				facingDirection.getY() * DEFAULT_BULLET_SPEED);
		body.addFixture(bodyFixture);
		body.setBullet(true);
		body.setMass();
		return body;
	}
}
