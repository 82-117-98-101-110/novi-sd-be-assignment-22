package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "AvatarPostDto")
public class AvatarPostDto {

	@Schema(
		type = "string",
		description = "Password",
		example = "https://avatarlinks.ravel.world/388838",
		required = true
	)
	@NotBlank
	private String avatarUrl;
}
