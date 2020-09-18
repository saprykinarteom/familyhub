package ru.saprykinav.familyhub.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.repository.CustomerRepository;
import ru.saprykinav.familyhub.repository.FamilyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FamilyService {
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    CustomerRepository customerRepository;

    public boolean save(Family family) {
        familyRepository.save(family);
        return true;
    }
    public Family loadByFamilyId(Long familyId) throws NotFoundException {
        Family familyFromDb = familyRepository.findById(familyId).orElseThrow(RuntimeException::new);
        //расчет покупок, совершенных после последнего дня платежа
        BigDecimal sumBuysFromDB = familyRepository.findSumPriceAllByFamilyByCustomerIdAndDateBetween(familyId,familyFromDb.getLastPayDay(), LocalDate.now()).orElse(BigDecimal.ZERO);
        familyFromDb.setSumBuysAfterLastPayDay(sumBuysFromDB);
        //подгрузка списка членов семьи
        List<Customer> customersFromDB = customerRepository.findAllByFamilyId(familyId);
        familyFromDb.setCustomers(customersFromDB);
        return familyFromDb;
    }
    public void setLastPayDay(Long familyId, LocalDate lastPayDay) {
        Family familyFromDb = familyRepository.findById(familyId).orElseThrow(RuntimeException::new);
        familyFromDb.setLastPayDay(lastPayDay);
        familyRepository.save(familyFromDb);
    }
}
