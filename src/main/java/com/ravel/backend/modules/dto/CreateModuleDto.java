package com.ravel.backend.modules.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "CreateModuleDto")
public class CreateModuleDto {

	@NotNull(message = "Please provide a appModule name")
	@Column(name = "module_name", nullable = false, unique = true, length = 50)
	@NotBlank
	private String moduleName;

	@Column(name = "module_description")
	private String description;
}
