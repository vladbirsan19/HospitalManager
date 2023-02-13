package com.siit.hospital_manager.model.dto;

import com.siit.hospital_manager.model.AppointmentStatus;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Patient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Setter
@Getter
@RequiredArgsConstructor
public class CreateAppointmentDto {

    @NotNull(message = "Date can not be null")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Future(message="Appointments can be created starting with current date and time")
    private LocalDateTime date;

    private Patient patient;

    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
}
