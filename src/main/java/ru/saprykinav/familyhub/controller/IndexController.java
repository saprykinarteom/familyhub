package ru.saprykinav.familyhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.saprykinav.familyhub.service.CustomerService;

@RestController
public class IndexController {
    @Autowired
    CustomerService customerService;
    @GetMapping("/")
    public ResponseEntity<String> Index() {
       customerService.deleteUser((long) 4);
       customerService.deleteUser((long) 5);
       return new ResponseEntity<String>("zaebis", HttpStatus.OK);
    }
}
