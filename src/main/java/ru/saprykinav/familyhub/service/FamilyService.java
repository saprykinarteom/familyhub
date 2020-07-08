package ru.saprykinav.familyhub.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.repository.FamilyRepository;

import java.util.Optional;

@Service
public class FamilyService {
    @Autowired
    FamilyRepository familyRepository;

    public boolean save(Family family) {
        familyRepository.save(family);
        return true;
    }
    public Family findByFamilyId(Long familyId) throws NotFoundException {
        Optional<Family> familyFromDb = familyRepository.findById(familyId);
        if(familyFromDb.isEmpty()){
            throw new NotFoundException("Family not found");
        }
        return familyFromDb.get();
    }
}
