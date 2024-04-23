package ru.dimgel.wf0.dispatch;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


@SuppressWarnings("unused")
public class DispatcherUtil {

	/**
	 * <p>Splits request path to components and URL-decodes them, allowing {@link Dispatcher} implementations
	 * to conveniently iterate over path components. Ignores empty path components:</p>
	 *
	 * <pre>
	 * ""                           -> []
	 * "/?a=1"                      -> []
	 * "hello/world"                -> ["hello", "world"]
	 * "///hello///world%3F///?a=1" -> ["hello", "world?"]
	 * </pre>
	 */
	public static ArrayList<String> splitRequestPath(String uri) {
		int uriLength = uri.length();

		var x = new ArrayList<String>(20);  // Same as Vector but not thread-safe.

		int nonEmptyComponentStart = -1;
		int i;
		for (i = 0;  i < uriLength;  i++) {
			char c = uri.charAt(i);
			if (c == '?') {
				break;
			}
			if (c == '/') {
				if (nonEmptyComponentStart >= 0) {
					x.add(URLDecoder.decode(uri.substring(nonEmptyComponentStart, i), StandardCharsets.UTF_8));
					nonEmptyComponentStart = -1;
				}
			} else if (nonEmptyComponentStart < 0) {
				nonEmptyComponentStart = i;
			}
		}
		if (nonEmptyComponentStart >= 0) {
			x.add(URLDecoder.decode(uri.substring(nonEmptyComponentStart, i), StandardCharsets.UTF_8));
		}

		// NOT adding EOF marker, because I often test two sibling components for emptiness, and on second test iterator.next() throws.
//		x.add("");

		return x;
	}

	@SuppressWarnings("unused")
	public static ArrayList<String> splitRequestPath(HttpServletRequest rq) {
		return splitRequestPath(rq.getRequestURI());
	}
}
