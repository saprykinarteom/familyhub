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
import java.util.Optional;

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
    public Family findByFamilyId(Long familyId) throws NotFoundException {
        Optional<Family> familyFromDb = familyRepository.findById(familyId);
        if(familyFromDb.isEmpty()){
            throw new NotFoundException("Family not found");
        }
        //расчет покупок за последний месяц
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        Optional<BigDecimal> sumBuysFromDB = familyRepository.findSumPriceAllByFamilyByCustomerIdAndDateBetween(familyId,dateFrom, dateTo);
        familyFromDb.get().setLastMonthBuys(sumBuysFromDB.get());

        //подгрузка списка членов семьи
        List<Customer> customersFromDB = customerRepository.findAllByFamilyId(familyId);
        familyFromDb.get().setCustomers(customersFromDB);
        return familyFromDb.get();
    }
}
