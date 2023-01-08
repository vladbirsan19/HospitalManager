package com.SIIT.HospitalManager.model.dto;

import com.SIIT.HospitalManager.model.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDto {
    private Integer id;
    private LocalDateTime date;
    private Patient patient;

}