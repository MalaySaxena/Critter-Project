package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerToDTO(userService.addNewCustomer(customerDTO));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customerList = userService.getAllCustomers();
        for(Customer customer:customerList){
            customerDTOS.add(customerToDTO(customer));
        }
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return customerToDTO(userService.getCustomerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeToDTO(userService.addNewEmployee(employeeDTO));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeToDTO(userService.getEmployeeDTO(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.addEmployeesSchedule(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        List<Employee> employees = userService.getEmployeesOnAvailability();

        List<EmployeeDTO> availableEmployees = new ArrayList<>();

        for(Employee employee:employees){
            //System.out.println(employee.getDaysWorking());

            if(employee.getSkills()==null&&!skills.isEmpty()){
                continue;
            }

            if(employee.getDaysWorking()==null){
                continue;
            }

            if(employee.getSkills().containsAll(skills)&&employee.getDaysWorking().contains(day)){
                availableEmployees.add(employeeToDTO(employee));
            }

        }
        return availableEmployees;
    }

    private CustomerDTO customerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);

        if(customer.getPetList()!=null){
            List<Pet> pets = customer.getPetList();
            List<Long> petIds = new ArrayList<>();
            for(int i=0;i<pets.size();i++){
                petIds.add(pets.get(i).getId());
            }

            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    private EmployeeDTO employeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
