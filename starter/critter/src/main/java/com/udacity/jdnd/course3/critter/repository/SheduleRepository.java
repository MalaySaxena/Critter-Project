package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.models.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface SheduleRepository extends CrudRepository<Schedule, Integer> {
}
