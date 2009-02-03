package br.com.caelum.selenium.grid;

import java.lang.reflect.Constructor;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {

	private final URLClassLoader classLoader;
	private final List<Server> upServers;
	
	public CommandExecutor(URLClassLoader classLoader) {
		this.classLoader = classLoader;
		upServers = new ArrayList<Server>();
	}

	public void execute(String hubURL, String environment, String host, int port) {
		try {
			Class<?> type = classLoader
					.loadClass("com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControl");
			Constructor<?> constructor = type.getConstructor(String.class, String.class, String.class, String.class);
			Object remoteControl = constructor.newInstance(hubURL, environment, host, "" + (port));
			type.getMethod("register").invoke(remoteControl);
			// Will not start another Selenium Server on the same port
			if (!isUp(port)) {  
				type.getMethod("launch", String[].class).invoke(remoteControl, (Object)(new String[] { "-port", port + "" }));
				upServers.add(new Server(port));
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private boolean isUp(int port) {
		for (Server server : upServers) {
			if (server.port == port) {
				return true;
			}
		}
		return false;
	}

	private static class Server {
		int port;
		public Server(int port) {
			this.port = port;
		}
	}
}
