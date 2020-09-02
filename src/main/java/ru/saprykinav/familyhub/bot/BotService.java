package ru.saprykinav.familyhub.bot;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.service.CustomerService;
import ru.saprykinav.familyhub.service.FamilyService;

import java.util.Optional;

@Service
public class BotService {
    @Autowired
    CustomerService customerService;
    @Autowired
    FamilyService familyService;

    public Optional<Customer> authorization(Message inMessage) {
        Optional<Customer> customerFromDb = customerService.loadCustomerByTgUsername(inMessage.getChat().getUserName());
        return customerFromDb;
    }
    public String getFamilyInfo(Customer customer) throws NotFoundException {
        Family family = familyService.loadByFamilyId(customer.getFamily().getId());
        String text = "Состав семьи:\n" + family.getCustomers().toString() + "\n" + "Обязательные траты: " + family.getMandatorySpending().toString() + "\n" + "Траты за текущий месяц: " + family.getLastMonthBuys().toString();
        return text;
    }
}
