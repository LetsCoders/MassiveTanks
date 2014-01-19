package pl.letscode.tanks.atmosphere;

import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.websocket.WebSocketEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Debug only
 * @author edhendil
 *
 */
public class WebSocketEventListenerImpl implements WebSocketEventListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WebSocketEventListenerImpl.class);
	
	@Override
	public void onPreSuspend(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource presuspend",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	@Override
	public void onSuspend(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource suspend",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	@Override
	public void onResume(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource resume",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	/**
	 * This method is always invoked when tab is reloaded or closed on any browser.
	 */
	@Override
	public void onDisconnect(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource disconnect",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	@Override
	public void onBroadcast(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource broadcast",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	@Override
	public void onThrowable(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource throwable",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	@Override
	public void onClose(AtmosphereResourceEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : resource close",
				event.getResource().getRequest().getSession(true).getId(), event.getResource().uuid());
	}

	@Override
	public void onHandshake(WebSocketEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : websocket handshake",
				event.webSocket().resource().getRequest().getSession(true).getId(), event.webSocket().resource().uuid());
	}

	@Override
	public void onMessage(WebSocketEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : websocket message",
				event.webSocket().resource().getRequest().getSession(true).getId(), event.webSocket().resource().uuid());
	}

	@Override
	public void onClose(WebSocketEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : websocket close",
				event.webSocket().resource().getRequest().getSession(true).getId(), event.webSocket().resource().uuid());
	}

	@Override
	public void onControl(WebSocketEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : websocket control",
				event.webSocket().resource().getRequest().getSession(true).getId(), event.webSocket().resource().uuid());
	}

	@Override
	public void onDisconnect(WebSocketEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : websocket disconnect",
				event.webSocket().resource().getRequest().getSession(true).getId(), event.webSocket().resource().uuid());
	}

	@Override
	public void onConnect(WebSocketEvent event) {
		LOGGER.info("sessionId: {}, resourceId: {} : websocket connect",
				event.webSocket().resource().getRequest().getSession(true).getId(), event.webSocket().resource().uuid());
	}

}
