package com.ravel.backend.security.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
@Data
public class AuthenticationRequest {

	@Email
	@Schema(description = "email", type = "string", example = "developer@ravel.systems")
	@NotBlank(message = "email is mandatory")
	private String email;

	@Schema(description = "password", type = "string", example = "whatever")
	@NotBlank(message = "password is mandatory")
	private String password;
}
