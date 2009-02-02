package br.com.caelum.selenium.grid;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class JettyServer {

	public JettyServer() {
		this(4443);
	}
	public JettyServer(int port) {
		super();
		this.port = port;
	}

	private final int port;

	public void start(CommandExecutor executor) throws Exception {
        final Server server = new Server(port);

        final ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        final Context root = new Context(contexts, "/", Context.SESSIONS);

        root.addServlet(new ServletHolder(new RemoteControlStartupServlet(executor)),  "/remote-control-manager/start");
        root.addServlet(new ServletHolder(new PingServlet()),  "/ping");
        server.start();
        server.join();

	}
}
