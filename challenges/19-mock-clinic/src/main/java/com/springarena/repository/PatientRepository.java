package com.springarena.repository;

import com.springarena.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctor(String doctor);
    List<Patient> findByCondition(String condition);
}
