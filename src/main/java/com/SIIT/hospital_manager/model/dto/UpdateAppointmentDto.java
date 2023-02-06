package com.siit.hospital_manager.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateAppointmentDto {

    private Integer id;

    @NotNull(message = "Date can't be null")
    private LocalDateTime date;
}
