package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "UserDetailsGetDto", description = "")
public class UserDetailsGetDto {

	private UUID userUUID;
	private String firstName;
	private String lastName;
	private String email;
	private String avatarUrl;
	private String avatarUrlFullBody;
	private String profileImageUrl;
}
