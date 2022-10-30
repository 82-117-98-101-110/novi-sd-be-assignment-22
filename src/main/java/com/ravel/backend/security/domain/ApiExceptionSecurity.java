package com.ravel.backend.security.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class ApiExceptionSecurity {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Date timeStamp;

	private int status;
	private String error;
	private String message;

	public ApiExceptionSecurity(int status, String error, String message) {
		this.timeStamp = new Date();
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
