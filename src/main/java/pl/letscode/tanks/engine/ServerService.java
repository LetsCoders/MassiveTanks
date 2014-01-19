package pl.letscode.tanks.engine;

import javax.inject.Inject;

public class ServerService {

	private final GameRepository gameRepository;
	private final GameFactory gameFactory;
	
	@Inject
	public ServerService(GameRepository gameRepository, GameFactory gameFactory) {
		this.gameRepository = gameRepository;
		this.gameFactory = gameFactory;
	}
	
	public void createNewGame(long gameId) {
		Game game = this.gameFactory.createGame(gameId);
		this.gameRepository.save(game);
	}
	
}
