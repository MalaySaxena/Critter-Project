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

    public Schedule addSchedule(ScheduleDTO scheduleDTO){
        List<Employee> employeesList = userService.getAllEmployeesById(scheduleDTO.getEmployeeIds());
        List<Pet> petList = petService.getAllPetsByIds(scheduleDTO.getPetIds());

        Schedule newSchedule = new Schedule(employeesList, petList, scheduleDTO.getDate(), scheduleDTO.getActivities());
        newSchedule = scheduleRepository.save(newSchedule);
        return newSchedule;
    }

    public List<Schedule> getAllSchedules(){
        List<Schedule> allSchedules = (List<Schedule>)scheduleRepository.findAll();
        return allSchedules;
    }

    public List<Schedule> getAllSchedulesForPet(Long petId){
        List<Schedule> allSchedules = scheduleRepository.findAllByPetList(petService.getPet(petId));
        return allSchedules;
    }

    public List<Schedule> getAllSchedulesForEmployee(Long employeeId){
        List<Schedule> allSchedules = scheduleRepository.findAllByEmployeeList(userService.getEmployee(employeeId));
        return allSchedules;
    }

    public List<Schedule> getAllSchedulesForCustomer(Long customerId){
        Customer customer = userService.getCustomer(customerId.intValue());

        List<Schedule> allSchedules = new ArrayList<>();

        for(Pet pet:customer.getPetList()){
            allSchedules.addAll(scheduleRepository.findAllByPetList(pet));
        }

        return allSchedules;
    }

}
