package com.ravel.backend.spaceLite.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SpaceLiteGetDto {

	private final String name;
	private final String src;
	private final String embeddedCode;
}
