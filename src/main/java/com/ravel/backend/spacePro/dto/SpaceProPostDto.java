package com.ravel.backend.spacePro.dto;

import com.ravel.backend.spacePro.enumeration.SpaceType;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class SpaceProPostDto implements Serializable {

	@NotNull
	private  String spaceName;

	private  String description;
	private  boolean codeProtected;
	private  SpaceType spaceType;
	private  String environmentName;
	private String defaultSpaceRole;
	private UUID userUuid;
	String organizationName;
}
