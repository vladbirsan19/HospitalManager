package com.siit.hospital_manager.model;


import com.siit.hospital_manager.model.dto.CreateDoctorDto;
import com.siit.hospital_manager.model.dto.DoctorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name="doctors")
@Getter
@Setter
@PrimaryKeyJoinColumn(referencedColumnName = "user_id")
@SuperBuilder
@RequiredArgsConstructor
public class Doctor extends User{

    @NotNull(message="Name should not be null")
    @Pattern(regexp = "([A-Z][a-z]{1,15}(\\s|-))+[A-Z][a-z]{1,15}")
    @Schema(description = "This is the doctor full name. Must start with uppercase letter for each word")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "specialisation_id", nullable = false)
    private Specialisation specialisation;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<Appointment> appointments;

    public DoctorDto toDto() {
        return DoctorDto
                .builder()
                .name(name)
                .id(getId())
                .specialisation(specialisation)
                .build();
    }

}