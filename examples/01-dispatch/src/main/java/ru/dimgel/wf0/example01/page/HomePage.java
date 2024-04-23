package ru.dimgel.wf0.example01.page;

import ru.dimgel.wf0.dispatch.Page;


public class HomePage extends Page {

	@Override
	protected void service() throws Exception {

		// As you might have noticed, here I use pure Servlet API.
		response.setContentType("text/html; charset=UTF-8");
		try (var w = response.getWriter()) {
			w.print("""
					<!DOCTYPE html>
					<html>
					<head><title>wf0-example01-dispatch</title></head>
					<body><p>
					<a href='hello'>Say hello.</a><br/>
					<a href='hello/world'>Say hello world.</a>
					</p></body>
					</html>""");
		}
	}
}
