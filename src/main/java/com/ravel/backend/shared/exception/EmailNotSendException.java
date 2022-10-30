package com.ravel.backend.shared.exception;

public class EmailNotSendException extends RuntimeException {

	public EmailNotSendException(String message) {
		super(message);
	}
}
