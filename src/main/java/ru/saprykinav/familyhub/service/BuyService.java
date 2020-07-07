package ru.saprykinav.familyhub.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.repository.BuyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BuyService  {
    @Autowired
    private BuyRepository buyRepository;

    public List<Buy> findAll(Long customerId) throws NotFoundException {
        List<Buy> buys = buyRepository.findAllByCustomerId(customerId);
        if(buys.isEmpty()) {
            throw new NotFoundException("Buys not found");
        }
        return buys;
    }
    public List<Buy> findAllInLastMonth(Long customerId) throws NotFoundException {
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        List<Buy> buys = buyRepository.findAllByCustomerIdAndDateBetween(customerId, dateFrom, dateTo);
        if(buys.isEmpty()) {
            throw new NotFoundException("Buys not found");
        }
        return buys;
    }
    public Buy saveBuy (Buy buy) {
            return buyRepository.save(buy);
    }
    public BigDecimal getLastMonthBuys (Long customerId) throws NotFoundException  {
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        Optional<BigDecimal> lastMonthBuys = buyRepository.findSumPriceAllByCustomerIdAndDateBetween(customerId, dateFrom, dateTo);
        if(lastMonthBuys.isEmpty()) throw new NotFoundException("not working");
        return lastMonthBuys.get();
    }
}
