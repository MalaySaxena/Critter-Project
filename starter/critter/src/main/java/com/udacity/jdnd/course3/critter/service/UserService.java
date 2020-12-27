package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
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

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public CustomerDTO addNewCustomer(CustomerDTO newCustomer){
        Customer customer = customerRepository.save(new Customer(newCustomer.getName(), newCustomer.getPhoneNumber()));
        CustomerDTO addedCustomer = new CustomerDTO();
        BeanUtils.copyProperties(customer, addedCustomer);
        return addedCustomer;
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();

        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for(Customer customer:customerList){
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer,customerDTO);
            customerDTOS.add(customerDTO);
        }

        return customerDTOS;
    }

    public EmployeeDTO addNewEmployee(EmployeeDTO newEmployee){
        Employee employee = employeeRepository.save(new Employee(newEmployee.getName(), newEmployee.getSkills()));
        return employeeToDTO(employee);
    }

    public EmployeeDTO getEmployee(Long id){
        Employee employee = employeeRepository.findById(id).get();
        return employeeToDTO(employee);
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

    public EmployeeDTO employeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        System.out.println(employee.getDaysWorking());
        System.out.println(employeeDTO.getDaysWorking());
        return employeeDTO;
    }

    public List<EmployeeDTO> getEmployeesOnAvailability(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();

        DayOfWeek day = date.getDayOfWeek();

        List<EmployeeDTO> availableEmployees = new ArrayList<>();

        for(Employee employee:employees){
            if(employee.getSkills().containsAll(skills) && employee.getDaysWorking().contains(day)){
                availableEmployees.add(employeeToDTO(employee));
            }
        }
        return availableEmployees;
    }
}
