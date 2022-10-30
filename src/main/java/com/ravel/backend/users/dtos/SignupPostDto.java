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
@Schema(name = "SignupPostDto", description = "")
public class SignupPostDto {

	@Schema(
		type = "string",
		description = "First Name of the user",
		example = "John",
		required = true
	)
	@Size(min = 1, max = 50)
	@NotBlank
	private String firstName;

	@Schema(
		type = "string",
		description = "Last Name of the user",
		example = "Doe",
		required = true
	)
	@Size(min = 1, max = 50)
	@NotBlank
	private String lastName;

	@Schema(
		type = "string",
		description = "Email of the user",
		example = "John@email.com",
		required = false
	)
	@Size(min = 1, max = 50)
	@Email
	private String email;

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
