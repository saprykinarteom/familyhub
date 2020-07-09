package ru.saprykinav.familyhub.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.service.FamilyService;


@RestController
@RequestMapping("/family")
public class FamilyController {
    @Autowired
    FamilyService familyService;
    @PostMapping("/setms")
    public ResponseEntity<Family> setMandatorySpending(@RequestBody Family family) throws NotFoundException {
        familyService.save(family);
        try {
        return ResponseEntity.ok(familyService.findByFamilyId(family.getId()));
        } catch (NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity("Family not found", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/")
    public ResponseEntity<Family> findFamilyByCustomerId(@AuthenticationPrincipal Customer customer) throws NotFoundException {
        try {
            Family familyFromDB = familyService.findByFamilyId(customer.getFamily().getId());
            return ResponseEntity.ok(familyFromDB);

        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity("huy", HttpStatus.BAD_REQUEST);
        }
    }

}
