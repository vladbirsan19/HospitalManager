package com.siit.hospital_manager.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
public class UpdatePatientDto {

    @NotNull(message = "First name can not be null")
    @Pattern(regexp = "([A-Z][a-z]{1,20}[\\s\\-]?)+")
    @Schema(description = "This is the patients full name. Must start with uppercase letter for each word")
    private String firstName;

    @NotNull(message = "Last Name can not be null")
    @Pattern(regexp = "([A-Z][a-z]{1,20}[\\s\\-]?)+")
    @Schema(description = "This is the patients full name. Must start with uppercase letter for each word")
    private String lastName;

    @Range(min = 0, max = 120)
    @NotNull(message = "Age can't be null")
    private Integer age;

    @NotNull(message = " E-mail address can't be null")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @Schema(description = "This is the patient's email address. " + "It validates email addresses such as :" +
            "username@domain.com\n" +
            "user.name@domain.com\n" +
            "user-name@domain.com\n" +
            "username@domain.co.in\n" +
            "user_name@domain.com")
    private String email;

    @NotNull(message = "phoneNumber can not be null")
    @Pattern(regexp = "0\\d{9}")
    private String phoneNumber;

    private Boolean isActive;

}
