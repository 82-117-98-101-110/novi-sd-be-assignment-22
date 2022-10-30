package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "RestPasswordRequest", description = "")
public class ResetPasswordRequest {

	@Schema(
		type = "Email",
		description = "Email",
		example = "email@email.com",
		required = true
	)
	@Size(min = 1, max = 100)
	@NotBlank
	@Email
	private String email;
}
