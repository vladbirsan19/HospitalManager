package com.SIIT.HospitalManager.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateAppointmentDto {

    private Integer id;
    private LocalDateTime date;

}