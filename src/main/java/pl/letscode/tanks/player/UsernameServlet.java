package pl.letscode.tanks.player;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.letscode.tanks.guice.GuiceInjector;
import pl.letscode.tanks.session.Session;

import com.google.inject.Injector;

/**
 * Servlet used to choose name and join the game.
 */
public class UsernameServlet extends HttpServlet {

	private static final long serialVersionUID = 8628963769752471982L;
	private static final String USERNAME = "playerName";
	private static final String FORM_URL = "/index.jsp";
	private static final String GAME_URL = "/client/index.html";

	private static final Injector INJECTOR = GuiceInjector.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Session sessions = INJECTOR.getInstance(Session.class);
		String sessionId = request.getSession(true).getId();
		Player player = sessions.get(sessionId);

		if (player != null) {
			request.getRequestDispatcher(GAME_URL).forward(request, response);
		} else {
			request.getRequestDispatcher(FORM_URL).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Session session = INJECTOR.getInstance(Session.class);
		String sessionId = request.getSession(true).getId();

		String username = request.getParameter(USERNAME);
		
		Player player = session.get(sessionId);
		
		PlayerFactory factory = INJECTOR.getInstance(PlayerFactory.class);
		PlayerRepository repository = INJECTOR.getInstance(PlayerRepository.class);
		
		// what if player pressed F5
		if (player != null) {
			request.getRequestDispatcher(GAME_URL).forward(request, response);
		} else if (username != null && !repository.existsByName(username)) {
			Player newPlayer = factory.createPlayer(username);
			session.put(sessionId, newPlayer);
			repository.save(newPlayer);
			request.getRequestDispatcher(GAME_URL).forward(request, response);
		} else {
			request.getRequestDispatcher(FORM_URL).forward(request, response);
		}
	}

}
