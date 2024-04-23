package ru.dimgel.wf0.dispatch;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * <p>Request handler for specific URL.</p>
 *
 * <p>Instances are stateful and temporary, a new instance is created by {@link Dispatcher} for each HTTP request.
 * E.g. for URI {@code "/hello"} dispatcher may create and return instance of some {@code class HelloPage extends Page}.</p>
 */
public abstract class Page {

	// Filled by Servlet.
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	// Called by Servlet.
	protected abstract void service() throws Exception;
}
