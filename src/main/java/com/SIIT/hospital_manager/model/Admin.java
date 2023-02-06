package com.siit.hospital_manager.model;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name="admins")
@PrimaryKeyJoinColumn(referencedColumnName = "user_id")
@SuperBuilder
@RequiredArgsConstructor
public class Admin extends User {
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}