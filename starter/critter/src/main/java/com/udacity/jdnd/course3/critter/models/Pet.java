package com.udacity.jdnd.course3.critter.models;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private int id;
    private PetType type;
    private String name;
    private LocalDate dateOfBirth;
    private String notes;

    @ManyToOne
    private Customer owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
