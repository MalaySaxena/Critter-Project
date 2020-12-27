package com.udacity.jdnd.course3.critter.models;

import com.udacity.jdnd.course3.critter.models.enums.EmployeeSkill;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    private Set<DayOfWeek> daysWorking;

    public Employee() {
    }

    public Employee(String name, Set<EmployeeSkill> skills) {
        this.name = name;
        this.skills = skills;
    }

    @ElementCollection
    private Set<EmployeeSkill> skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Set<DayOfWeek> getDaysWorking() {
        return daysWorking;
    }

    public void setDaysWorking(Set<DayOfWeek> daysWorking) {
        this.daysWorking = daysWorking;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public void setName(String name) {
        this.name = name;
    }


}
