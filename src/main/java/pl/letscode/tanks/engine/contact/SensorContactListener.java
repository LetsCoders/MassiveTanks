package pl.letscode.tanks.engine.contact;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactPoint;

public abstract class SensorContactListener<K, L> extends ContactAdapter {

	private Class<K> type;
	private Class<L> withType;

	public SensorContactListener(Class<K> type, Class<L> withType) {
		this.type = type;
		this.withType = withType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sensed(ContactPoint point) {
		Object userData1 = point.getBody1().getUserData();
		Object userData2 = point.getBody2().getUserData();
		if (userData1 == null || userData2 == null) {
			return;
		}
		if (type.isInstance(userData1) && withType.isInstance(userData2)) {
			onContact((K) userData1, (L) userData2);
		}
		if (type.isInstance(userData2) && withType.isInstance(userData1)) {
			onContact((K) userData2, (L) userData1);
		}
	}

	public abstract void onContact(K k, L l);

}
