package com.siit.hospital_manager.model.dto;


import com.siit.hospital_manager.model.Specialisation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class UpdateDoctorDto {

    @NotNull(message="Name should not be null")
    @NotEmpty(message="Name should not be empty")
    @Pattern(regexp = "([A-Z][a-z]{1,15}(\\s|-))+[A-Z][a-z]{1,15}")
    @Schema(description = "This is the doctor full name. Must start with uppercase letter for each word")
    private String name;

    @Schema(description = "This is the specialisation name")
    private Specialisation specialisation;

}