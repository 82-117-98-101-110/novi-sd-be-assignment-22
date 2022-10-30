package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "ResetPasswordPost", description = "")
public class ResetPasswordPost {

	@Schema(
		type = "string",
		description = "Password",
		example = "LongPassword@",
		required = true
	)
	@Size(min = 6, max = 24)
	@NotBlank
	private String password;

	@Schema(
		type = "string",
		description = "Password",
		example = "LongPassword@",
		required = false
	)
	@Size(min = 6, max = 24)
	@NotBlank
	private String passwordValidate;
}
