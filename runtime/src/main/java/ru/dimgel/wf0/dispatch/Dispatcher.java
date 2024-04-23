package ru.dimgel.wf0.dispatch;


import jakarta.servlet.http.HttpServletRequest;


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
