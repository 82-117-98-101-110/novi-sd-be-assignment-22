package com.ravel.backend.security.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
@Data
public class VerifyCode {

	private String clientId;
	private String code;
}
