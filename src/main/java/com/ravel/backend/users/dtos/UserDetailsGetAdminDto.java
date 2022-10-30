package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "UserDetailsGetAdminDto", description = "")
public class UserDetailsGetAdminDto {

	private UUID userUUID;
	private String firstName;
	private String lastName;
	private String email;
	private String avatarUrl;
	private String avatarUrlFullBody;
	private String profileImageUrl;
	private boolean isActive;
	private boolean isNotLocked;
	private String role;
	private String[] authorities;
}
