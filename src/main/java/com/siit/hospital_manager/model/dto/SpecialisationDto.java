package com.siit.hospital_manager.model.dto;


import com.siit.hospital_manager.model.Specialisation;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialisationDto {

    SpecialisationDto specialisationDto;
    private Integer id;

    private String name;

    public SpecialisationDto(Specialisation specialisation) {
        this.id = specialisation.getId();
        this.name = specialisation.getName();
    }

}
