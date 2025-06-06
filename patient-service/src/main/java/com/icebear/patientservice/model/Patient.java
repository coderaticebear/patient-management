package com.icebear.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Patient {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.AUTO) //Auto Generate Primary KEY {Denoted by strategy}
    private UUID id; //Generated as UUID

    @NotNull
    private String name; //Name cannot be null

    @NotNull
    @Email //Email jakarta validation
    @Column(unique = true) //It has to be unique
    private String email;

    @NotNull
    private String address;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate registeredDate;

    //Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public @NotNull LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(@NotNull LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }
}
