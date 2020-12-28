package com.udacity.jdnd.course3.critter.models;

import com.udacity.jdnd.course3.critter.models.enums.PetType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private long id;
    private PetType type;
    private String name;
    private LocalDate dateOfBirth;
    private String notes;

    public Pet(PetType type, String name, LocalDate dateOfBirth, String notes, Customer owner) {
        this.type = type;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.notes = notes;
        this.owner = owner;
    }

    public Pet() {
    }

    @ManyToOne
    private Customer owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

}
