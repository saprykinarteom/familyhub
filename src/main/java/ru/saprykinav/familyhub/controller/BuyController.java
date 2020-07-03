package ru.saprykinav.familyhub.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.BuyService;

import java.util.List;

@RestController
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private BuyService buyService;

    @GetMapping("/all")
    public ResponseEntity<List<Buy>> getAll(@AuthenticationPrincipal Customer customer) throws NotFoundException {
        try {
            return ResponseEntity.ok(buyService.findAll(customer.getId()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity("customers buys not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Buy> addBuy(@AuthenticationPrincipal Customer customer, @RequestBody Buy buy){
        return ResponseEntity.ok(buyService.saveBuy(buy));
    }
}
