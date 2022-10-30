package com.ravel.backend.shared.exception.apiException;

import static org.springframework.http.HttpStatus.*;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ravel.backend.shared.exception.*;
import java.io.IOException;
import java.util.Objects;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ApiExceptionHandler implements ErrorController {

	private static final String ACCOUNT_LOCKED =
		"Your account has been locked, please reset your password to unlock-your account";
	private static final String METHOD_IS_NOT_ALLOWED =
		"This request method is not allowed on this endpoint. Please send a '%s' request";
	private static final String INCORRECT_CREDENTIALS =
		"Cannot authenticate with credentials";
	private static final String ERROR_PROCESSING_FILE =
		"Error occurred while processing file";

	private static final Logger LOGGER = LoggerFactory.getLogger(
		ApiExceptionHandler.class
	);

	@ExceptionHandler(value = JWTVerificationException.class)
	public ResponseEntity<ApiException> JWTVerificationException(ApiRequestException e) {
		return createApiException(UNAUTHORIZED, e.getMessage());
	}

	@ExceptionHandler(value = ApiRequestException.class)
	public ResponseEntity<ApiException> handleApiRequestException(ApiRequestException e) {
		return createApiException(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<ApiException> handleNotFoundException(NotFoundException e) {
		return createApiException(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<ApiException> badRequestException(BadRequestException e) {
		return createApiException(BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(value = VrClientAuthException.class)
	public ResponseEntity<ApiException> vrClientAuthException(VrClientAuthException e) {
		return createApiException(BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(value = InviteException.class)
	public ResponseEntity<ApiException> inviteException(InviteException e) {
		return createApiException(BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(value = AlreadyExistException.class)
	public ResponseEntity<ApiException> handleAlreadyExists(AlreadyExistException e) {
		return createApiException(HttpStatus.CONFLICT, e.getMessage());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiException> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e
	) {
		return createApiException(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(value = EmailNotSendException.class)
	public ResponseEntity<ApiException> emailNotSendException(ConnectionException e) {
		return createApiException(EXPECTATION_FAILED, e.getMessage());
	}

	@ExceptionHandler(value = ConnectionException.class)
	public ResponseEntity<ApiException> connectionAlreadyExists(ConnectionException e) {
		return createApiException(HttpStatus.CONFLICT, e.getMessage());
	}

	@ExceptionHandler(value = FileNotUploadedException.class)
	public ResponseEntity<ApiException> fileNotuploaded(FileNotUploadedException e) {
		return createApiException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}

	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiException> maxUploadSizeExceeded(
		MaxUploadSizeExceededException e
	) {
		return createApiException(HttpStatus.PAYLOAD_TOO_LARGE, e.getMessage());
	}

	@ResponseStatus(NOT_IMPLEMENTED)
	@ExceptionHandler(value = NotImplemented.class)
	public ResponseEntity<ApiException> notImplemented(NotImplemented e) {
		return createApiException(HttpStatus.NOT_IMPLEMENTED, e.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiException> badCredentialsException() {
		return createApiException(BAD_REQUEST, INCORRECT_CREDENTIALS);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiException> accessDenied() {
		return createApiException(HttpStatus.UNAUTHORIZED, "Authentication needed");
	}

	@ExceptionHandler(HttpClientErrorException.Forbidden.class)
	public ResponseEntity<ApiException> forbiddenException() {
		return createApiException(HttpStatus.FORBIDDEN, "Not enough privileges");
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ApiException> disabledException() {
		return createApiException(FORBIDDEN, "Account is disabled");
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<ApiException> lockedException() {
		return createApiException(FORBIDDEN, ACCOUNT_LOCKED);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<ApiException> tokenExpiredException(
		TokenExpiredException exception
	) {
		return createApiException(UNAUTHORIZED, exception.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiException> methodNotSupportedException(
		HttpRequestMethodNotSupportedException exception
	) {
		HttpMethod supportedMethod = Objects
			.requireNonNull(exception.getSupportedHttpMethods())
			.iterator()
			.next();
		return createApiException(
			METHOD_NOT_ALLOWED,
			String.format(METHOD_IS_NOT_ALLOWED, supportedMethod)
		);
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<ApiException> notFoundException(NoResultException exception) {
		LOGGER.error(exception.getMessage());
		return createApiException(NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiException> iOException(IOException exception) {
		LOGGER.error(exception.getMessage());
		return createApiException(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
	}

	private ResponseEntity<ApiException> createApiException(
		HttpStatus httpStatus,
		String message
	) {
		return new ResponseEntity<>(
			new ApiException(httpStatus.value(), httpStatus.getReasonPhrase(), message),
			httpStatus
		);
	}
}
