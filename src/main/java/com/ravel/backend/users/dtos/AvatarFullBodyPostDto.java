package com.ravel.backend.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(name = "AvatarFullBodyPostDto")
public class AvatarFullBodyPostDto {

    @Schema(
            type = "string",
            description = "avatarUrl",
            example = "https://avatarlinks.ravel.world/388838",
            required = true
    )
    @NotBlank
    private String avatarUrl;
}

