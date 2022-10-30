package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "GetSelf", description = "")
public class GetSelfDto {

	private UUID userUUID;
	private String firstName;
	private String lastName;
	private String email;
	private String avatarUrl;
	private String profileImageUrl;
	private String systemRole;
	private String[] systemAuthorities;
	private String avatarUrlFullBody;
}
