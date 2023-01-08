package com.SIIT.HospitalManager.model.dto;

import com.SIIT.HospitalManager.model.Patient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PatientDto {

    private Integer id;
    private String name;
    private Integer age;
    private String phoneNumber;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.age = patient.getAge();
        this.phoneNumber = patient.getPhoneNumber();
    }

}