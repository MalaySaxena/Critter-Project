package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private PetService petService;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, @Lazy PetService petService) {
        this.customerRepository = customerRepository;
        this.petService = petService;
        this.employeeRepository = employeeRepository;
    }

    public Customer addNewCustomer(CustomerDTO newCustomer){
        Customer customer = customerRepository.save(new Customer(newCustomer.getName(), newCustomer.getPhoneNumber()));
        return customer;
    }

    public Customer addNewCustomer(Customer customer){
        Customer customerAdded = customerRepository.save(customer);
        return customerAdded;
    }

    public Customer getCustomer(int id){
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        return customerList;
    }

    public Customer getCustomerByPet(Long id){
        return petService.getOwnerOfPet(id);
    }

    public Employee addNewEmployee(EmployeeDTO newEmployee){
        Employee employee = employeeRepository.save(new Employee(newEmployee.getName(), newEmployee.getDaysWorking(), newEmployee.getSkills()));
        return employee;
    }

    public Employee getEmployeeDTO(Long id){
        return getEmployee(id);
    }

    public Employee getEmployee(Long id){
        Employee employee = employeeRepository.findById(id).get();
        return employee;
    }

    public List<Employee> getAllEmployeesById(List<Long> ids){
        if(ids == null)
            return null;
        List<Employee> employeeList = (List<Employee>) employeeRepository.findAllById(ids);
        return employeeList;
    }

    public void addEmployeesSchedule(Long id, Set<DayOfWeek> days){
        Employee employee = employeeRepository.findById(id).get();

        if(employee.getDaysWorking() == null){
            employee.setDaysWorking(new HashSet<>());
        }

        for(DayOfWeek day:days){
            employee.getDaysWorking().add(day);
        }

        employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesOnAvailability(){
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        return employees;
    }


}
