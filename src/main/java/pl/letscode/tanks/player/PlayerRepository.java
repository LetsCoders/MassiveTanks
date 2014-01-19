package pl.letscode.tanks.player;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

@Singleton
public class PlayerRepository {

	private Set<Player> entities;
	
	public PlayerRepository() {
		this.entities = new HashSet<Player>();
	}
	
	public long count() {
		return this.entities.size();
	}
	
	public void delete(Player entity) {
		this.entities.remove(entity);
	}
	
	public void deleteAll() {
		this.entities.clear();
	}
	
	public Iterable<Player> findAll() {
		return this.entities;
	}
	
	public Player findByName(String name) {
		for (Player entity : this.entities) {
			if (entity.getName().equals(name)) {
				return entity;
			}
		}
		return null;
	}
	
	public boolean existsByName(String name) {
		for (Player entity : this.entities) {
			if (entity.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void save(Player entity) {
		this.entities.add(entity);
	}
	
}
