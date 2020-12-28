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

    public CustomerDTO addNewCustomer(CustomerDTO newCustomer){
        Customer customer = customerRepository.save(new Customer(newCustomer.getName(), newCustomer.getPhoneNumber()));
        return customerToDTO(customer);
    }

    public CustomerDTO addNewCustomer(Customer customer){
        Customer customerAdded = customerRepository.save(customer);
        return customerToDTO(customerAdded);
    }

    public Customer getCustomer(int id){
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();

        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for(Customer customer:customerList){
            customerDTOS.add(customerToDTO(customer));
        }

        return customerDTOS;
    }

    public CustomerDTO getCustomerByPet(Long id){
        return customerToDTO(petService.getOwnerOfPet(id));
    }

    public EmployeeDTO addNewEmployee(EmployeeDTO newEmployee){
        Employee employee = employeeRepository.save(new Employee(newEmployee.getName(), newEmployee.getDaysWorking(), newEmployee.getSkills()));
        return employeeToDTO(employee);
    }

    public EmployeeDTO getEmployeeDTO(Long id){
        return employeeToDTO(getEmployee(id));
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

    public List<EmployeeDTO> getEmployeesOnAvailability(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();

        DayOfWeek day = date.getDayOfWeek();

        List<EmployeeDTO> availableEmployees = new ArrayList<>();

        for(Employee employee:employees){
            //System.out.println(employee.getDaysWorking());
            if(employee.getSkills()==null&&!skills.isEmpty()){
                continue;
            }

            if(employee.getDaysWorking()==null){
                continue;
            }

            if(employee.getSkills().containsAll(skills)&&employee.getDaysWorking().contains(date.getDayOfWeek())){
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
