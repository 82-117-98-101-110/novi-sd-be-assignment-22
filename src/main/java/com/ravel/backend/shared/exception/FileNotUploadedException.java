package com.ravel.backend.shared.exception;

public class FileNotUploadedException extends RuntimeException {

	public FileNotUploadedException(String message) {
		super(message);
	}
}
