package ru.dimgel.wf0.example02;

import ru.dimgel.wf0.dispatch.Dispatcher;
import ru.dimgel.wf0.dispatch.Servlet;


public class ServletImpl extends Servlet {

	@Override
	protected Dispatcher createDispatcher() {
		return new DispatcherImpl();
	}
}
