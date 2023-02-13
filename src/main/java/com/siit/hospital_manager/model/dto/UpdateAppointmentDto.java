package com.siit.hospital_manager.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class UpdateAppointmentDto {

    private Integer id;

    private String note;

    private String diagnostic;

    private String treatment;

}
