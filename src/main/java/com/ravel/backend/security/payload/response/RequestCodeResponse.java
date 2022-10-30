package com.ravel.backend.security.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
@Data
public class RequestCodeResponse {

	private String code;
	private OffsetDateTime createdAt;
	private OffsetDateTime expiresAt;

	public RequestCodeResponse(
		String code,
		OffsetDateTime createdAt,
		OffsetDateTime expiresAt
	) {
		this.code = code;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
}
