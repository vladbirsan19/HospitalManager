package com.siit.hospital_manager.model.dto;;


import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialisation;
import lombok.Builder;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Integer id;
    private String name;
    private Specialisation specialisation;
    private Boolean isActive;

    public DoctorDto(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.specialisation = doctor.getSpecialisation();
        this.isActive = doctor.isActive();
    }
}