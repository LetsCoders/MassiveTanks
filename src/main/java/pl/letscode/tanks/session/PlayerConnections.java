package pl.letscode.tanks.session;

import java.util.concurrent.ConcurrentHashMap;

import org.atmosphere.cpr.AtmosphereResource;

import pl.letscode.tanks.player.Player;

import com.google.inject.Singleton;

@Singleton
public class PlayerConnections extends
		ConcurrentHashMap<Player, AtmosphereResource> {

	private static final long serialVersionUID = -1945950969520746178L;

}
