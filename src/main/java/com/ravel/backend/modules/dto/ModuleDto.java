package com.ravel.backend.modules.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "ModuleGetDto")
public class ModuleDto {

	String modules;
}
