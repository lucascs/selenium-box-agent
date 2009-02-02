package br.com.caelum.selenium.grid;

import java.lang.reflect.Constructor;
import java.net.URLClassLoader;

public class CommandExecutor {

	private final URLClassLoader classLoader;

	public CommandExecutor(URLClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void execute(String hubURL, String environment, String host, int port) {
		try {
			Class<?> type = classLoader
					.loadClass("com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControl");
			Constructor<?> constructor = type.getConstructor(String.class, String.class, String.class, String.class);
			Object remoteControl = constructor.newInstance(hubURL, environment, host, "" + (port));
			type.getMethod("register").invoke(remoteControl);
			type.getMethod("launch", String[].class).invoke(remoteControl, (Object)(new String[] { "-port", port + "" }));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
