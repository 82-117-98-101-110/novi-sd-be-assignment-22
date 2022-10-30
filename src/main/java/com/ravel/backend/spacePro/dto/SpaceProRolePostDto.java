package com.ravel.backend.spacePro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "SpaceProRolePostDto")
public class SpaceProRolePostDto {

	private String appRoleName;
}
