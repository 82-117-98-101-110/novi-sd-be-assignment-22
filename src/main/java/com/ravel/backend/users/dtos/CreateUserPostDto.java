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
@Schema(name = "CreateUserPostDto", description = "")
public class CreateUserPostDto {

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
		example = "Dpe",
		required = true
	)
	@Size(min = 1, max = 50)
	@NotBlank
	private String lastName;

	@Schema(
		type = "string",
		description = "Email of the user",
		example = "John@email.com",
		required = true
	)
	@Size(min = 1, max = 50)
	@NotBlank
	@Email
	private String email;

	@Schema(
		description = "System Role of the user",
		example = "USER_ROLE",
		required = true
	)
	@Size(min = 6, max = 24)
	@NotBlank
	private String role;

	@Schema(
		type = "string",
		description = "Password",
		example = "LongPassword@",
		required = true
	)
	@Size(min = 6, max = 24)
	@NotBlank
	private String password;
}
