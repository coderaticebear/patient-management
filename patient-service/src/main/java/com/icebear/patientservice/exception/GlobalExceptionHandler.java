package com.icebear.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/*
 * We are using exception handler to control the response that goes out of controller
 * This is important that client only need to know what matters
 * */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);

        /*
         * This accepts any exceptions caused
         * Creating a hashmap that only include the details needed
         * returns the response entity as a badRequest with Hashmap as body
         * */
    }
    /*
    * Follows are exception that could arise from service
    * */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.warn("Email already exists: " + ex.getMessage()); //Log for developer in terminal
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email already exists!");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.warn("Patient not found: " + ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not found!");
        return ResponseEntity.badRequest().body(errors);
    }
}
