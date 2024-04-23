package ru.dimgel.wf0.example01.page;

import ru.dimgel.wf0.dispatch.Page;


public class HelloPage extends Page {
	String name;

	public HelloPage(String name) {
		this.name = name;
	}

	@Override
	protected void service() throws Exception {
		response.setContentType("text/plain; charset=UTF-8");
		try (var w = response.getWriter()) {
			w.format("Hello %s!\n", name);
		}
	}
}
