package ru.dimgel.wf0.dispatch;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public abstract class Servlet extends HttpServlet {
	private Dispatcher dispatcher;

	/**
	 * Called once by {@link #init(ServletConfig)}, returned object is cached.
	 */
	protected abstract Dispatcher createDispatcher();

	@Override
	public void init(ServletConfig c) throws ServletException {
		super.init(c);
		dispatcher = createDispatcher();
	}


	/**
	 * <p>Called by {@link #service(HttpServletRequest, HttpServletResponse)}. Must NOT throw!</p>
	 * 
	 * <p>Override this e.g. to log real message somewhere, and return logged error code.</p>
	 *
	 * @return  Error message to show to user. By default, it's {@code e.getMessage()}.
	 */
	protected String logInternalServerError(Throwable e) {
		return e.getMessage();
	}


	@Override
	protected void service(HttpServletRequest rq, HttpServletResponse re) {
		var m = RequestMethod.fromString(rq.getMethod());
		try {
			var p = dispatcher.createPage(rq, m);
			p.request = rq;
			p.response = re;
			p.method = m;
			p.service();
			re.flushBuffer();
		} catch (WebError e) {
			var msg = (e.httpStatus == 500) ? logInternalServerError(e) : e.getMessage();
			sendError(rq, re, m, e.httpStatus, msg);
		} catch (Throwable e) {
			var msg = logInternalServerError(e);
			sendError(rq, re, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
		}
	}


	protected void sendError(HttpServletRequest rq, HttpServletResponse re, RequestMethod m, int httpStatus, String errorMessage) {
		try {
			var p = dispatcher.createErrorPage(rq, httpStatus, errorMessage);
			p.request = rq;
			p.response = re;
			p.method = m;
			p.service();
			re.flushBuffer();
		} catch (Throwable _) {
			// createErrorPage() failed, or response is already committed, or whatever. Nothing we can do.
		}
	}
}
