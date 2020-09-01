package ru.saprykinav.familyhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.saprykinav.familyhub.entity.Customer;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BotService {
    @Autowired
    CustomerService customerService;

    public Optional<Customer> authorization(Message inMessage) {
        Optional<Customer> customerFromDb = customerService.loadCustomerByTgUsername(inMessage.getChat().getUserName());
        return customerFromDb;
    }



}
