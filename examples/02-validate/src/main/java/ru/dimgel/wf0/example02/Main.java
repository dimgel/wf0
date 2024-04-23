package ru.dimgel.wf0.example02;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class Main {

	private static void throwUsage() throws Exception {
		throw new Exception("Usage: [listen-port]");
	}

	public static void main(String[] args) throws Exception {
		// Parse command line.
		int port = 8080;
		if (args.length > 1) {
			throwUsage();
		}
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Throwable _) {
				throwUsage();
			}
			if (port < 1 || port > 65535) {
				throwUsage();
			}
		}

		// Start embedded jetty server.
		var sch = new ServletContextHandler();
		sch.addServlet(ServletImpl.class, "/");

		var srv = new Server(port);
		srv.setHandler(sch);
		srv.start();
	}
}
