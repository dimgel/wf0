package ru.dimgel.wf0.example01;

import ru.dimgel.wf0.dispatch.Page;


// This class demonstrates how to add custom instance vars to Page.
// Class page.HelloPage extends this one.
public abstract class MyPage extends Page {
	protected String userAgent;

	@Override
	protected final void service() throws Exception {
		userAgent = request.getHeader("User-Agent");
		myService();
	}

	protected abstract void myService() throws Exception;
}
