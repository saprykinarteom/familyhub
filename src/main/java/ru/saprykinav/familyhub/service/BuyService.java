package ru.saprykinav.familyhub.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.repository.BuyRepository;

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
    public Buy saveBuy (Buy buy) {
            return buyRepository.save(buy);
    }
}
