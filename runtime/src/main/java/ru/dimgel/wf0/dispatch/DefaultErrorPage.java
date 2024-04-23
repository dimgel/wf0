package ru.dimgel.wf0.dispatch;


public class DefaultErrorPage extends Page {
	protected final int httpStatus;
	protected final String errorMessage;

	public DefaultErrorPage(int httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	@Override
	protected void service() throws Exception {
		if (errorMessage != null && !errorMessage.isEmpty()) {
			response.sendError(httpStatus, errorMessage);
		} else {
			response.sendError(httpStatus);
		}
	}
}
