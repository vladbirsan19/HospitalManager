package com.SIIT.HospitalManager.model.dto;

public class UpdatePatientDto {

    private Integer age;
    private Integer id;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UpdatePatientDto() {
    }

    public UpdatePatientDto(Integer age, Integer id) {
        this.age = age;
        this.id = id;
    }
}
