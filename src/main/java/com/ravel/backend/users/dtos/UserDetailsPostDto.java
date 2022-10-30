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
@Schema(name = "UserDetailsPostDto")
public class UserDetailsPostDto {

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
}
