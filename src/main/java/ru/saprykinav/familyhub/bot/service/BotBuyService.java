package ru.saprykinav.familyhub.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.BuyService;

import java.math.BigDecimal;

@Service
public class BotBuyService {
    @Autowired
    BuyService buyService;

    public String addBuy(String inMessage, Customer customer) throws NumberFormatException{
        try {
            System.out.println(inMessage);
            BigDecimal price = new BigDecimal(inMessage);
            buyService.saveBuy(new Buy(customer, price));
            return "Готово!";
        }
        catch (NumberFormatException e){
            return "Ошибка! Введено не число";
        }
    }
}
