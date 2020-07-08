package ru.saprykinav.familyhub.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.entity.Credit;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.CreditService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    private CreditService creditService;

    @GetMapping("/all")
    public ResponseEntity<List<Credit>> getAll(@AuthenticationPrincipal Customer customer) throws NotFoundException {
        try {
            return ResponseEntity.ok(creditService.findAll(customer.getId()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity("customers credits not found", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/last")
    public ResponseEntity<List<Credit>> getAllInLastMount(@AuthenticationPrincipal Customer customer) throws NotFoundException {
        try {
            return ResponseEntity.ok(creditService.findAllInLastMonth(customer.getId()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity("customers credits not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Credit> addCredit(@AuthenticationPrincipal Customer customer, @RequestBody Credit credit){
        return ResponseEntity.ok(creditService.saveCredit(credit));
    }

    @GetMapping("/last/sum")
    public ResponseEntity<BigDecimal> getSumAllInLastMount(@AuthenticationPrincipal Customer customer) throws NotFoundException {
        try {
            return ResponseEntity.ok(creditService.getLastMonthCredits(customer.getId()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity("Credits not found", HttpStatus.BAD_REQUEST);
        }
    }
}
