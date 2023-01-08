package com.SIIT.HospitalManager.model.dto;


import com.SIIT.HospitalManager.model.Patient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateAppointmentDto {

    private LocalDateTime date;
    private Patient patient;


}

