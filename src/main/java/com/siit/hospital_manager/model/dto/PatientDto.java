package com.siit.hospital_manager.model.dto;


import com.siit.hospital_manager.model.Patient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String phoneNumber;
    private Boolean isActive;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        this.age = patient.getAge();
        this.phoneNumber = patient.getPhoneNumber();
        this.isActive = patient.isActive();
    }

}
