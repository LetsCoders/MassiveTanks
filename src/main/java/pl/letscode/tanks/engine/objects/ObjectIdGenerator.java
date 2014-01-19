package pl.letscode.tanks.engine.objects;

import com.google.inject.Singleton;

@Singleton
public class ObjectIdGenerator {

	private long lastId = 0;

	public long getNextId() {
		this.lastId++;
		return lastId;
	}
}
