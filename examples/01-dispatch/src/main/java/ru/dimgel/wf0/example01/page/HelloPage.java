package ru.dimgel.wf0.example01.page;

import ru.dimgel.wf0.example01.MyPage;


// Instead of extending wf0's class Page and overriding Page.service() method,
// this class extends user's custom class MyPage (which introduces instance variable `userAgent`) and overrides MyPage.myService().
public class HelloPage extends MyPage {
	String name;

	public HelloPage(String name) {
		this.name = name;
	}

	@Override
	protected void myService() throws Exception {
		response.setContentType("text/plain; charset=UTF-8");
		try (var w = response.getWriter()) {
			w.format("Hello %s!\n\nYour User-Agent is: \"%s\"", name, userAgent);
		}
	}
}
