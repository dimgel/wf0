package ru.dimgel.wf0.dispatch;

import java.security.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Not used by fw0 itself (for now), used by example01.
 */
public enum RequestMethod {
	UNKNOWN("UNKNOWN"),

	// Alphabetic order.
	CONNECT("COMMENT"),
	DELETE("DELETE"),
	GET("GET"),
	HEAD("HEAD"),
	OPTIONS("OPTIONS"),
	PATCH("PATCH"),
	POST("POST"),
	PUT("PUT"),
	TRACE("TRACE");


	private final String s;

	RequestMethod(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}

	/**
	 * Does NOT throw on unknown {@code s} value, because that would complicate error handling
	 * in {@link Servlet#service(HttpServletRequest, HttpServletResponse)}.
	 *
	 * @param s   "GET", "POST", etc.
	 * @return   Never null.
	 */
	public static RequestMethod fromString(String s) {
		return switch (s) {
			// Usage frequency order (just in case).
			case "GET" -> GET;
			case "POST" -> POST;
			case "PUT" -> PUT;
			case "DELETE" -> DELETE;
			case "PATCH" -> PATCH;
			case "HEAD" -> HEAD;
			case "CONNECT" -> CONNECT;
			case "TRACE" -> TRACE;
			default -> UNKNOWN;
		};
	}
}
