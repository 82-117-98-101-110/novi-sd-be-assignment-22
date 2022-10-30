package com.ravel.backend.spaceLite.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class SpaceLitePostDto implements Serializable {

	private final String name;
	private final String src;
	private final String roomCode;
	private final String inviteLink;
	private final String embeddedCode;
}
