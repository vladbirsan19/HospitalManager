package com.siit.hospital_manager.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UpdateAppointmentDto {

    @NotNull
    private Integer id;
    @NotNull
    private String note;
    @NotNull
    private String diagnostic;
    @NotNull
    private String treatment;

}
