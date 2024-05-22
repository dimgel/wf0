package ru.dimgel.wf0.example02;

import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;
import ru.dimgel.wf0.dispatch.Dispatcher;
import ru.dimgel.wf0.dispatch.Page;
import ru.dimgel.wf0.dispatch.RequestMethod;
import ru.dimgel.wf0.example02.page.HomePage;


public class DispatcherImpl extends Dispatcher {
	private final Pattern uriRegex = Pattern.compile("/(\\?.*)?");

	@Override
	public Page createPage(HttpServletRequest rq, RequestMethod m) {
		return uriRegex.matcher(rq.getRequestURI()).matches()
				? new HomePage()
				: null;
	}
}
