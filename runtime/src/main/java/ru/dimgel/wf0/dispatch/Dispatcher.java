package ru.dimgel.wf0.dispatch;

import jakarta.servlet.http.HttpServletRequest;


/**
 * <b>ATTENTION</b>: Single instance is created & cached by {@link Servlet} and used for all requests,
 * so implementations must be stateless & thread-safe.
 */
public abstract class Dispatcher {

	/**
	 * Construct and return appropriate Page subclass instance, based on rq.getRequestURI() & rq.getRequestMethod().
	 * Return null for 404.
	 */
	public abstract Page createPage(HttpServletRequest rq);

	public Page createErrorPage(HttpServletRequest rq, int httpStatus, String errorMessage) {
		return new DefaultErrorPage(httpStatus, errorMessage);
	}
}
