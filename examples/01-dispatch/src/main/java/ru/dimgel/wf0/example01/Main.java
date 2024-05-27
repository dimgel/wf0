package ru.dimgel.wf0.example01;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class Main {

	public static void main(String[] args) throws Exception {
		var ctx = new ServletContextHandler();
		ctx.addServlet(ServletImpl.class, "/");

		var srv = new Server(8080);
		srv.setHandler(ctx);
		srv.start();
	}
}
