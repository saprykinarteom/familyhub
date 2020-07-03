package ru.saprykinav.familyhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.CustomerService;

@RestController
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/registration")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        if(!customerService.saveCustomer(customer)) return new ResponseEntity("user with this username is present",HttpStatus.BAD_REQUEST);
        return new ResponseEntity(HttpStatus.OK);
    }
}

