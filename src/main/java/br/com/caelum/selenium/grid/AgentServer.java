package br.com.caelum.selenium.grid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;


public class AgentServer {
	public static void main(String[] args) throws Exception {
        // i cannot create any direct reference to JettyServer otherwise the
        // java virtual machine would load in my current classloader the JettyServer and
        // jetty classes, causing version errors between the required jetty version for
        // the web app and the jetty version used by selenium
		
		JettyServer j = new JettyServer();
		String server = "/selenium-server-1.0-SNAPSHOT.jar";
		String rc = "/selenium-grid-remote-control-standalone-1.0.4-SNAPSHOT.jar";
		j.start(new CommandExecutor(new URLClassLoader(new URL[] {urlFor(server), urlFor(rc)}, null)));
		
		
    }

	private static URL urlFor(String x) throws IOException {
		URL url = AgentServer.class.getResource(x);
		File jar = File.createTempFile("selenium", ".jar");
		
		FileOutputStream os = new FileOutputStream(jar);
		FileInputStream is = new FileInputStream(url.getFile());
		
		FileChannel in = is.getChannel();
		
		in.transferTo(0, in.size(), os.getChannel());
		return jar.toURI().toURL();
	}
}
