package com.ravel.backend.organization.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AddUserToOrg")
public class AddUserToOrg {

	private String organizationName;
	private String userEmail;
	private String organizationRole;
}
