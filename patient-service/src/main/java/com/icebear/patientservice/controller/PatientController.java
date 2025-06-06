package com.icebear.patientservice.controller;

import com.icebear.patientservice.dto.PatientRequestDTO;
import com.icebear.patientservice.dto.PatientResponseDTO;
import com.icebear.patientservice.dto.validators.CreatePatientValidationGroup;
import com.icebear.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients") //This controller handles every requests starting with patient
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        /*
        * Dependency Injection
        * Injecting the service to controller
        * */
    }

    @GetMapping()
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        /*
        * ResponseEntity has a List of PatientResponseDTO
        * */
        List<PatientResponseDTO> patientResponseDTOs = patientService.getPatients();
        return ResponseEntity.ok(patientResponseDTOs);

        /*
        * ResponseEntity - To create HTTP Responses to send to Frontend
        * Getting all the info
        * ok() Set status of http response as 200
        * */
    }

    @PostMapping()
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok(patientResponseDTO);
        /*
        * Receives Data from frontend to create patient
        * Calls patientService to create patient and passes the DTO
        * Patient service uses mapper to convert DTO to entity object and save to DB
        * It returns the created patient entity which gets converted to DTO
        * Controller uses this DTO to send response to frontend with ok() status
        * */
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(patientRequestDTO, id);
        return ResponseEntity.ok(patientResponseDTO);

        /*
        * Receives the ID of a patient and the data needs to be updated as DTO
        * This id is passed to patient service to update the patient
        * in return controller gets a patientResponse DTO of the updated patient
        * Returns to Frontend with ok() status
        * */
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
