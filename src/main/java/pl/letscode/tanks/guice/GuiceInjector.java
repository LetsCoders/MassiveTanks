package pl.letscode.tanks.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceInjector {

	private static Injector INSTANCE;

	public static Injector getInstance() {
		if (INSTANCE == null) {
			INSTANCE = Guice.createInjector(new TankModule());
		}
		return INSTANCE;
	}

}
