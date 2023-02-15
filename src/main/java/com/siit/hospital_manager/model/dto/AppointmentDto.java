package com.siit.hospital_manager.model.dto;


import com.siit.hospital_manager.model.AppointmentStatus;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Patient;
import lombok.*;
@Data
@Builder
public class AppointmentDto {
    private Integer id;
    private String date;
    private Patient patient;
    private Doctor doctor;
    private AppointmentStatus appointmentStatus;

}