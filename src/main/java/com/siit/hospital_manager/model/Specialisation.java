package com.siit.hospital_manager.model;


import com.siit.hospital_manager.model.dto.SpecialisationDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Entity
@Table(name="specialisations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Specialisation {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="specialisation_id")
    private Integer id;
    @NotNull(message="Specialisation name should not be null")
    @NotEmpty(message = "Specialisation name can not be empty")
    @Column(name="specialisation_name", unique=true)
    private String name;

    @OneToMany(mappedBy = "specialisation", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Doctor> doctors;

    public SpecialisationDto toDto() {

        return SpecialisationDto.builder()
                .id(id)
                .name(name)
                .build();
    }

//    ALLERGY_AND_IMMUNOLOGY("Allergy and immunology"),
//    ANESTHESIOLOGY("Anesthesiology"),
//    DERMATOLOGY("Dermatology"),
//    DIAGNOSTIC_RADIOLOGY("Diagnostic radiology"),
//    EMERGENCY_MEDICINE("Emergency medicine"),
//    FAMILY_MEDICINE("Family medicine"),
//    INTERNAL_MEDICINE("Internal medicine"),
//    MEDICAL_GENETICS("Medical genetics"),
//    NEUROLOGY("Neurology"),
//    NUCLEAR_MEDICINE("Nuclear medicine"),
//    OBSTETRICS_AND_GYNECOLOGY("Obstetrics and gynecology"),
//    OPHTHALMOLOGY("Ophthalmology"),
//    PATHOLOGY("Pathology"),
//    PEDIATRICS("Pediatrics"),
//    PHYSICAL_MEDICINE_AND_REHABILITATION("Physical medicine and rehabilitation"),
//    PREVENTIVE_MEDICINE("Preventive medicine"),
//    PSYCHIATRY("Psychiatry"),
//    RADIATION_ONCOLOGY("Radiation oncology"),
//    SURGERY("Surgery"),
//    UROLOGY("Urology");

}