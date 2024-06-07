package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.CustomerEntity;
import com.platzi.pizza.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerEntity> getPhone(@PathVariable String phone){
        return ResponseEntity.ok(customerService.findByPhone(phone));
    }


}
