package com.siit.hospital_manager.model;


import com.siit.hospital_manager.model.dto.AppointmentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Date should not be null")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    //    @Future(message="Appointments can be created starting with current date and time")
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    public AppointmentDto toDto() {
        String dateFormat = "dd-MM-yyyy HH:mm";
        String formattedDate = date.format(DateTimeFormatter.ofPattern(dateFormat));

        return AppointmentDto
                .builder()
                .id(id)
                .date(formattedDate)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

}
