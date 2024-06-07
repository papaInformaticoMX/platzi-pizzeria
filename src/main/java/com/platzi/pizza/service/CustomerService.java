package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.CustomerEntity;
import com.platzi.pizza.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity findByPhone(String phone){
        return this.customerRepository.findByPhone(phone);
    }
}
