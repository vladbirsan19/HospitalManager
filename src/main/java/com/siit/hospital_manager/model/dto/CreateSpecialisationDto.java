package com.siit.hospital_manager.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CreateSpecialisationDto {

    @NotNull(message = "Specialisation name can not be null")
    @NotEmpty(message = "Specialisation name can not be empty")
    private String name;

}