package com.SIIT.HospitalManager.model.dto;


import org.hibernate.validator.constraints.Range;

public class UpdatePatientDto {

    Integer id;

    @Range(min = 0, max = 120)
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
