package com.siit.hospital_manager.repository;


import com.siit.hospital_manager.model.Specialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SpecialisationsRepository extends JpaRepository<Specialisation, Integer> {

    Optional<Specialisation> findByName(String name);

    List<Specialisation> findAllByIsActive(boolean isActive);

}
