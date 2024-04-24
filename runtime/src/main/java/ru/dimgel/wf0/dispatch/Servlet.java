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
		try {
			var p = dispatcher.createPage(rq);
			p.request = rq;
			p.response = re;
			p.service();
			re.flushBuffer();
		} catch (WebError e) {
			var msg = (e.httpStatus == 500) ? logInternalServerError(e) : e.getMessage();
			sendError(rq, re, e.httpStatus, msg);
		} catch (Throwable e) {
			var msg = logInternalServerError(e);
			sendError(rq, re, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
		}
	}


	protected void sendError(HttpServletRequest rq, HttpServletResponse re, int httpStatus, String errorMessage) {
		try {
			var p = dispatcher.createErrorPage(rq, httpStatus, errorMessage);
			p.request = rq;
			p.response = re;
			p.service();
			re.flushBuffer();
		} catch (Throwable _) {
			// createErrorPage() failed, or response is already committed, or whatever. Nothing we can do.
		}
	}
}
