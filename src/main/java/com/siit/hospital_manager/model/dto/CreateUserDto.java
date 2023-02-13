package com.siit.hospital_manager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class CreateUserDto {
    private final @NotBlank String userName;
    private final @NotBlank String password;

    @Schema(defaultValue = "ROLE_PATIENT", description = "MUST prefix all roles with ROLE_ !!!")
    private final @NotBlank String roles;
    private final boolean isActive;

}