package ru.saprykinav.familyhub.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.repository.BuyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    public BigDecimal getSumLastMonthBuys (Long customerId)  {
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        BigDecimal sumLastMonthBuys = buyRepository.findSumPriceAllByCustomerIdAndDateBetween(customerId, dateFrom, dateTo).orElse(BigDecimal.ZERO);
        return sumLastMonthBuys;
    }
    public BigDecimal getSumBuysByDatePeriod(Long customerId, LocalDate dateFrom, LocalDate dateTo){
        BigDecimal sumBuysByDatePeriod = buyRepository.findSumPriceAllByCustomerIdAndDateBetween(customerId, dateFrom, dateTo).orElse(BigDecimal.ZERO);
        return sumBuysByDatePeriod;
    }
}
