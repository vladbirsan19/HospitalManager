package com.SIIT.HospitalManager.model.dto;

import com.SIIT.HospitalManager.model.Doctor;
import com.SIIT.HospitalManager.model.Patient;
import lombok.*;

@Data
@Builder
public class AppointmentDto {
    private Integer id;
    private String date;
    private Patient patient;
    private Doctor doctor;

}