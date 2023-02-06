package com.siit.hospital_manager.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
public class UpdatePatientDto {

    private Integer id;

    @NotNull(message = "age can not be null")
    @Range(min = 0, max = 120)
    private Integer age;

    @NotNull(message = "phoneNumber can not be null")
    @Pattern(regexp = "0\\d{9}")
    private String phoneNumber;

}
