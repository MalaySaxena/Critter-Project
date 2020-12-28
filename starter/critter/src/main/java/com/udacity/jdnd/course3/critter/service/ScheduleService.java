package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private UserService userService;
    private PetService petService;
    private ScheduleRepository scheduleRepository;

    public ScheduleService(UserService userService, PetService petService, ScheduleRepository scheduleRepository) {
        this.userService = userService;
        this.petService = petService;
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO){
        List<Employee> employeesList = userService.getAllEmployeesById(scheduleDTO.getEmployeeIds());
        List<Pet> petList = petService.getAllPetsByIds(scheduleDTO.getPetIds());

        Schedule newSchedule = new Schedule(employeesList, petList, scheduleDTO.getDate(), scheduleDTO.getActivities());
        newSchedule = scheduleRepository.save(newSchedule);
        return scheduleToDTO(newSchedule);
    }

    public List<ScheduleDTO> getAllSchedules(){
        List<Schedule> allSchedules = (List<Schedule>)scheduleRepository.findAll();
        List<ScheduleDTO> allSchedulesDTO = new ArrayList<>();

        for(Schedule schedule:allSchedules){
            allSchedulesDTO.add(scheduleToDTO(schedule));
        }
        return allSchedulesDTO;
    }

    public List<ScheduleDTO> getAllSchedulesForPet(Long petId){
        List<Schedule> allSchedules = scheduleRepository.findAllByPetList(petService.getPet(petId));
        List<ScheduleDTO> allSchedulesDTO = getAllSchedulesDTO(allSchedules);
        return allSchedulesDTO;
    }

    public List<ScheduleDTO> getAllSchedulesForEmployee(Long employeeId){
        List<Schedule> allSchedules = scheduleRepository.findAllByEmployeeList(userService.getEmployee(employeeId));
        List<ScheduleDTO> allSchedulesDTO = getAllSchedulesDTO(allSchedules);
        return allSchedulesDTO;
    }

    public List<ScheduleDTO> getAllSchedulesForCustomer(Long customerId){
        Customer customer = userService.getCustomer(customerId.intValue());

        List<Schedule> allSchedules = new ArrayList<>();

        for(Pet pet:customer.getPetList()){
            allSchedules.addAll(scheduleRepository.findAllByPetList(pet));
        }

        List<ScheduleDTO> allSchedulesDTO = getAllSchedulesDTO(allSchedules);
        return allSchedulesDTO;
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
