package br.com.caelum.selenium.grid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoteControlStartupServlet extends HttpServlet {

	private final CommandExecutor executor;

	public RemoteControlStartupServlet(CommandExecutor executor) {
		this.executor = executor;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String hubURL = req.getParameter("hubURL");
		String environment = req.getParameter("environment");
		String host = req.getParameter("host");
		int port = Integer.parseInt(req.getParameter("portStart"));
		int n = Integer.parseInt(req.getParameter("quantity"));
		for (int i = 0; i < n; i++) {
//			try {
//				SelfRegisteringRemoteControlLauncher.main(new String[] {
//					"-port", "" + (port + i),
//					"-host", host,
//					"-env", environment,
//					"-hubURL", hubURL
//				});
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			executor.execute(hubURL, environment, host, port+i);
/*			SelfRegisteringRemoteControl remoteControl = new SelfRegisteringRemoteControl(hubURL, environment, host, "" + (port + i));
			remoteControl.register();
			try {
				remoteControl.launch(new String[] {"-port", port + ""});
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
	}
}
