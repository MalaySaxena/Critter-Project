package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.models.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleToDTO(scheduleService.addSchedule(scheduleDTO));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> allSchedules = scheduleService.getAllSchedules();
        return getAllSchedulesDTO(allSchedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> allSchedules = scheduleService.getAllSchedulesForPet(petId);
        List<ScheduleDTO> allSchedulesDTO = getAllSchedulesDTO(allSchedules);
        return allSchedulesDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> allSchedules = scheduleService.getAllSchedulesForEmployee(employeeId);
        List<ScheduleDTO> allSchedulesDTO = getAllSchedulesDTO(allSchedules);
        return allSchedulesDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = getAllSchedulesDTO(schedules);
        return scheduleDTOS;
    }

    private List<ScheduleDTO> getAllSchedulesDTO(List<Schedule> schedules){
        List<ScheduleDTO> allSchedulesDTO = new ArrayList<>();

        for(Schedule schedule:schedules){
            allSchedulesDTO.add(scheduleToDTO(schedule));
        }
        return allSchedulesDTO;
    }

    private ScheduleDTO scheduleToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> employeeIds = schedule.getEmployeeList()
                .stream()
                .map(employee -> employee.getId())
                .collect(Collectors.toList());

        List<Long> petIds = schedule.getPetList()
                .stream()
                .map(pet -> pet.getId())
                .collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }
}
