package com.siit.hospital_manager.model.dto;


import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialisation;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialisationDto {

    private Integer id;

    private String name;

    private Boolean isActive;

    public SpecialisationDto(Specialisation specialisation) {
        this.id = specialisation.getId();
        this.name = specialisation.getName();
        this.isActive = specialisation.isActive();
    }

}
