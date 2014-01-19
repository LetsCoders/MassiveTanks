package pl.letscode.tanks.events.server.json;

import java.util.Collection;

public class GameUpdateDTO {

	private final Collection<BulletHitDTO> bulletHits;
	private final Collection<ScoreUpdateDTO> frags;

	public GameUpdateDTO(Collection<BulletHitDTO> bulletHits,
			Collection<ScoreUpdateDTO> frags) {
		this.bulletHits = bulletHits;
		this.frags = frags;
	}

	public Collection<BulletHitDTO> getBulletHits() {
		return bulletHits;
	}
	
	public Collection<ScoreUpdateDTO> getFrags() {
		return frags;
	}
	
}
