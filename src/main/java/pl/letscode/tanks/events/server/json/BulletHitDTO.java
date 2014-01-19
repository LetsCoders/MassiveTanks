package pl.letscode.tanks.events.server.json;

public class BulletHitDTO implements ServerData {

	private long bulletId;
	private long hitObjectId;
	private int hp;
	
	public BulletHitDTO(long bulletId, long hitObjectId, int hp) {
		super();
		this.bulletId = bulletId;
		this.hitObjectId = hitObjectId;
		this.hp = hp;
	}
	public long getBulletId() {
		return bulletId;
	}
	public long getHitObjectId() {
		return hitObjectId;
	}
	public int getHp() {
		return hp;
	}
	
}
