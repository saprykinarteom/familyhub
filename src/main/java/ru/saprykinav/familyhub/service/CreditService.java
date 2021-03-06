package ru.saprykinav.familyhub.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Credit;
import ru.saprykinav.familyhub.repository.CreditRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    @Autowired
    CreditRepository creditRepository;

    public List<Credit> findAll(Long customerId) throws NotFoundException {
        List<Credit> credits = creditRepository.findAllByCustomerId(customerId);
        if(credits.isEmpty()) {
            throw new NotFoundException("Credits not found");
        }
        return credits;
    }
    public List<Credit> findAllInLastMonth(Long customerId) throws NotFoundException {
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        List<Credit> credits = creditRepository.findAllByCustomerIdAndDateBetween(customerId, dateFrom, dateTo);
        if(credits.isEmpty()) {
            throw new NotFoundException("Credits not found");
        }
        return credits;
    }
    public BigDecimal getLastMonthCredits (Long customerId) throws NotFoundException  {
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        Optional<BigDecimal> lastMonthCredits = creditRepository.findSumPriceAllByCustomerIdAndDateBetween(customerId, dateFrom, dateTo);
        if(lastMonthCredits.isEmpty()) throw new NotFoundException("Credits not found");
        return lastMonthCredits.get();
    }
    public Credit saveCredit (Credit credit) {
        return creditRepository.save(credit);
    }
}
