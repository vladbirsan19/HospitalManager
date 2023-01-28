package com.SIIT.HospitalManager.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDto {

    private Integer id;
    private String name;
    private String specialisation;
}