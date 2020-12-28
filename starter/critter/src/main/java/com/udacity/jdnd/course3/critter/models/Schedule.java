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
    private Long id;

    @ManyToMany
    private List<Employee> employeeList;

    @ManyToMany
    private List<Pet> petList;

    private LocalDate date;

    public Schedule() {
    }

    public Schedule(List<Employee> employeeList, List<Pet> petList, LocalDate date, Set<EmployeeSkill> activities) {
        this.employeeList = employeeList;
        this.petList = petList;
        this.date = date;
        this.activities = activities;
    }

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
