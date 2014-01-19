package pl.letscode.tanks.engine;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

@Singleton
public class GameRepository {
	
	private Set<Game> entities;
	
	public GameRepository() {
		this.entities = new HashSet<Game>();
	}
	
	public long count() {
		return this.entities.size();
	}
	
	public void delete(long id) {
		for (Game game : this.entities) {
			if (game.getId() == id) {
				entities.remove(game);
				break;
			}
		}
	}
	
	public void delete(Game entity) {
		this.entities.remove(entity);
	}
	
	public void deleteAll() {
		this.entities.clear();
	}
	
	public Iterable<Game> findAll() {
		return this.entities;
	}
	
	public Game findOne(long id) {
		for (Game game : this.entities) {
			if (game.getId() == id) {
				return game;
			}
		}
		return null;
	}
	
	public void save(Game entity) {
		this.entities.add(entity);
	}
}
