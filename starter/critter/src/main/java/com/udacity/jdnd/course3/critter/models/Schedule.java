package com.udacity.jdnd.course3.critter.models;

import com.udacity.jdnd.course3.critter.models.enums.EmployeeSkill;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<Employee> employeeList;

    @ManyToMany
    private List<Pet> petList;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
