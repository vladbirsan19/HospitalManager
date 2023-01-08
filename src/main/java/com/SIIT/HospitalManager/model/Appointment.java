package com.SIIT.HospitalManager.model;

import com.SIIT.HospitalManager.model.dto.AppointmentDto;
import com.SIIT.HospitalManager.model.dto.CreateAppointmentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "appointments")
@Getter
@Setter
@RequiredArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private Patient patient;

    public Appointment(CreateAppointmentDto createAppointmentDto) {
        this.date = createAppointmentDto.getDate();
        this.patient = createAppointmentDto.getPatient();
    }

    public AppointmentDto toDto(){
        return AppointmentDto
                .builder()
                .id(id)
                .date(date)
                .patient(patient)
                .build();
    }
}