package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {

    @Query("select s from Schedule s where :pet member of s.petList")
    List<Schedule> findAllByPetList(@Param("pet") Pet pet);

    @Query("select s from Schedule s where :employee member of s.employeeList")
    List<Schedule> findAllByEmployeeList(@Param("employee") Employee employee);

}
