package com.icebear.patientservice.repository;

import com.icebear.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
/*
* Repository inherits from JPARepository
* which gives the ability to perform CRUD operations on the entity specified
* {Patient entity in this case}
* */

    boolean existsByEmail(String email);

    /*
    * Adding a validation for email
    * Service need to check using this if the email already exists
    * JPA handles the process
    * */
    boolean existsByEmailAndIdNot(String email, UUID id);
    /*
    * This validation is for updating patients
    * where we want to ignore the patient we are updating to
    * check whether the email exists or not
    * */
}
