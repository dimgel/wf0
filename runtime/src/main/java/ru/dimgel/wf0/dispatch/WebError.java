package ru.dimgel.wf0.dispatch;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * <p>Caught by {@link Servlet#service(HttpServletRequest, HttpServletResponse)}.</p>
 *
 * <p>If {@code httpStatus == 500}, {@code Servlet.service()} calls {@link Servlet#logInternalServerError(Throwable)}.</p>
 *
 * <p>When catching other exception classes, {@code Servlet.service()} assumes {@code httpStatus = 500}.</p>
 */
public class WebError extends Exception {
	public final int httpStatus;

	public WebError(int httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public WebError(int httpStatus, Exception e) {
		this(httpStatus, e.getMessage());
	}

	public WebError(int httpStatus) {
		this(httpStatus, "");
	}
}
