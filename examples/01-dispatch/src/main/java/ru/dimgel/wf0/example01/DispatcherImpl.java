package ru.dimgel.wf0.example01;

import jakarta.servlet.http.HttpServletRequest;
import ru.dimgel.wf0.dispatch.Dispatcher;
import ru.dimgel.wf0.dispatch.DispatcherUtil;
import ru.dimgel.wf0.dispatch.Page;
import ru.dimgel.wf0.dispatch.RequestMethod;
import ru.dimgel.wf0.example01.page.HelloPage;
import ru.dimgel.wf0.example01.page.HomePage;


public class DispatcherImpl extends Dispatcher {

	@Override
	public Page createPage(HttpServletRequest rq, RequestMethod m) {

		// You can parse request URI and map it to Page subclass any way you like; e.g. in example02 I use regular expression.
		// But here I demonstrate splitRequestPath() utility which returns ArrayList of path components; e.g. "/hello/world" ---> ["hello", "world"].
		// It's rather verbose but very flexible to iterate over and extract path-encoded parameters.
		// I use basically the same approach in my `trac1` project written in C++, where it's also very efficient (scan URI in-place, use string_view).
		var i = DispatcherUtil.splitRequestPath(rq).iterator();

		if (!i.hasNext()) {
			return new HomePage();
		}

		var s = i.next();
		if ("hello".equals(s)) {
			if (!i.hasNext()) {
				return new HelloPage("stranger");
			}

			s = i.next();   // Path-encoded parameter.
			if (!i.hasNext()) {
				return new HelloPage(s);
			}

			return null;   // Got more path components after "/hello/{name}" ---> error 404.
		}

		return null;   // Error 404.
	}
}
