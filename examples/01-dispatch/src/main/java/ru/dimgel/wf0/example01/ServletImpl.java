package ru.dimgel.wf0.example01;

import ru.dimgel.wf0.dispatch.Dispatcher;
import ru.dimgel.wf0.dispatch.Servlet;


public class ServletImpl extends Servlet {

	// Our dispatcher is stateless and hence thread-safe, so we can pre-cache it.
	private final Dispatcher dispatcher = new DispatcherImpl();

	@Override
	protected Dispatcher createDispatcher() {
		return dispatcher;
	}
}
