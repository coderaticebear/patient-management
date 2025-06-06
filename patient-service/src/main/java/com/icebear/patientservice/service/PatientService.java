package com.icebear.patientservice.service;

import com.icebear.patientservice.dto.PatientRequestDTO;
import com.icebear.patientservice.dto.PatientResponseDTO;
import com.icebear.patientservice.exception.EmailAlreadyExistsException;
import com.icebear.patientservice.exception.PatientNotFoundException;
import com.icebear.patientservice.mapper.PatientMapper;
import com.icebear.patientservice.model.Patient;
import com.icebear.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        /*
         * Dependency Injection
         * Injecting PatientRepository Interface to PatientService as Dependency
         * To perform services by using the Repository which has access to Entity.
         * */
    }

    public List<PatientResponseDTO> getPatients() {
        /*
         * DTO - Data Transfer Object
         * We are using service to convert Entity to DTO  -> Controller
         * */
        List<Patient> patients = patientRepository.findAll();

        /*
         * Using the patient mapper helper class to convert the patients List of <Patient>
         * to DTO and returning that List of PatientResponse DTO
         * */
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient of this email already exists" + patientRequestDTO.getEmail());
        }
        /*
         * Checking is email already exists
         * Throws new exception (Runtime)
         * Handled by EmailAlreadyExistsException Class
         * This Exception is called in Global Exception handler which intercepts all
         * exception responses
         * */
        Patient newPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        /*
         * Need patient entity model object
         * Using Patient mapper we convert incoming DTO to entity model object
         * */
        return PatientMapper.toDTO(newPatient);
        /*
         * Returning the newly created patient entity object to mapper to
         * convert it to DTO for controller
         * */
    }

    public PatientResponseDTO updatePatient(PatientRequestDTO patientRequestDTO, UUID id) {
        /*
        * Use to update the patient info
        * Check whether the patient already exists or not
        * If not exists throws an exception
        * Check the email already exist - we don't want to use the email of an existing person when updating another one.
        * After update return the entity (converted to DTO using mapper)
        * */
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient of this email already exists" + patientRequestDTO.getEmail());
        }
        /*
        * We are not updating the registered date
        * For this reason we are not using the mapper as of now.
        * Its bad idea to write another function with one line less! LOL
        * */
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public void deletePatient(UUID id) {

        /*
        * Receives the ID of the patient needed to be deleted
        * Use patient repository directly to delete by ID
        * No DTO to entity conversion needed as there is only ID
        * */
        patientRepository.deleteById(id);
    }

}
