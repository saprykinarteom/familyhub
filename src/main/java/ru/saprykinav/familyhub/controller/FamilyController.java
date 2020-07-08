package ru.saprykinav.familyhub.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
