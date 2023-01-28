package com.SIIT.HospitalManager.model;

import com.SIIT.HospitalManager.model.dto.AppointmentDto;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    public AppointmentDto toDto() {
        String dateFormat = "MMM dd HH:mm";
        String formattedDate = date.format(DateTimeFormatter.ofPattern(dateFormat));

        return AppointmentDto
                .builder()
                .id(id)
                .date(formattedDate)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

    public Appointment() {
    }

    public Appointment(Integer id, LocalDateTime date, Patient patient) {
        this.id = id;
        this.date = date;
        this.patient = patient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}