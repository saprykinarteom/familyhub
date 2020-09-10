package ru.saprykinav.familyhub.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.repository.CustomerRepository;
import ru.saprykinav.familyhub.service.CustomerService;
import ru.saprykinav.familyhub.service.FamilyService;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    FamilyService familyService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/1")
    public ResponseEntity test1 (@AuthenticationPrincipal Customer customer){
        return ResponseEntity.ok(customerService.loadUserByUsername(customer.getUsername()));
    }
    @GetMapping("/2")
    public ResponseEntity test2 (@AuthenticationPrincipal Customer customer) throws NotFoundException {
        return ResponseEntity.ok(customerService.loadCustomerByTgUsername("SaprykinAV"));
    }
}
