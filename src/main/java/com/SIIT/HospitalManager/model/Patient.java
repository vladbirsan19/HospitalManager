package com.SIIT.HospitalManager.model;

import com.SIIT.HospitalManager.model.dto.CreatePatientDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="patients")
@Getter
@Setter
@RequiredArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    private String phoneNumber;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    List<Appointment> appointments;

    public Patient(CreatePatientDto createPatientDto){
        this.name = createPatientDto.getName();
        this.age = createPatientDto.getAge();
        this.phoneNumber = createPatientDto.getPhoneNumber();
    }



}