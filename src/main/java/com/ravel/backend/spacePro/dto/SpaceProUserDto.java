package com.ravel.backend.spacePro.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SpaceProUserDto implements Serializable {

	private final SpaceProUserDetailsDto spaceProUserDetails;
}
