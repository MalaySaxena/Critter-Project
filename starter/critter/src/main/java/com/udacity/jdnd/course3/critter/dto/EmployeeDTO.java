package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.models.enums.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private Long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysWorking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysWorking() {
        return daysWorking;
    }

    public void setDaysWorking(Set<DayOfWeek> daysWorking) {
        this.daysWorking = daysWorking;
    }
}
