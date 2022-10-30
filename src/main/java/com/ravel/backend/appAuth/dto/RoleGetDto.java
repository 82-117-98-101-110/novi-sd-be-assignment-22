package com.ravel.backend.appAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "RoleGetDto")
public class RoleGetDto {

	private String appRoleName;
	private String description;
	private String purpose;
}
