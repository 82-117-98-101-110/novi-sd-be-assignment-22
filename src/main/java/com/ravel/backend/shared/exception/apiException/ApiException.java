package com.ravel.backend.shared.exception.apiException;

import java.time.OffsetDateTime;

public class ApiException {

	private final OffsetDateTime timestamp;
	private final int status;
	private final String error;
	private final String message;

	public ApiException(int status, String error, String message) {
		this.error = error;
		this.message = message;
		this.timestamp = OffsetDateTime.now();
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public String getError() {
		return error;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return (
			"ApiException{" +
			"timestamp=" +
			timestamp +
			", status=" +
			status +
			", error='" +
			error +
			'\'' +
			", message='" +
			message +
			'\'' +
			'}'
		);
	}
}
