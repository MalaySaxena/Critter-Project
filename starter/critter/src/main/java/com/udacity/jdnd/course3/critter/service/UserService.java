package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        CustomerDTO createdCustomer = new CustomerDTO();
        BeanUtils.copyProperties(customer, createdCustomer);
        return createdCustomer;
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
}
