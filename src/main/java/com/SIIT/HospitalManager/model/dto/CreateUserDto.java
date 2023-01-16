package com.SIIT.HospitalManager.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class CreateUserDto {
    private final @NotBlank String userName;
    private final @NotBlank String password;
    private final @NotBlank String roles;
    private final boolean isActive;

}