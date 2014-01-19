package pl.letscode.tanks.session;

import java.util.concurrent.ConcurrentHashMap;

import pl.letscode.tanks.player.Player;

import com.google.inject.Singleton;

@Singleton
public class Session extends ConcurrentHashMap<String, Player> {

	private static final long serialVersionUID = 1314255501328112274L;

}
