package ru.saprykinav.familyhub.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.BotService;
import ru.saprykinav.familyhub.service.CustomerService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    BotService botService;
    @Autowired
    CustomerService customerService;

    private Long chat_id = Long.valueOf(0);
    private Customer customer;



    public void onUpdateReceived(Update update) {
        try {
            //проверяем есть ли сообщение и текстовое ли оно
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем объект входящего сообщения
                Message inMessage = update.getMessage();
                //проверяем авторизован ли пользователь
                if(!inMessage.getChatId().equals(chat_id)) {
                    System.out.println(inMessage.getText());
                    System.out.println(inMessage.getChat().getUserName());
                    customerService.test();
                    System.out.println(customerService.loadCustomerByTgUsername("SaprykinAV").get().getId());
                    authorization(inMessage);
                }
                //обрабатываем команду
                else{
                    input(inMessage);
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "@family_hub_bot";
    }

    public String getBotToken() {
        return "1372106827:AAGa_JAk-eMRfWnYQeBWkUhljuSOCiXyejc";
    }

    public void input(Message inMessage) throws TelegramApiException {
        if(inMessage.getText().contains("help") || inMessage.getText().contains("Помощь")){
            help();
        }
        else if (inMessage.getText().contains("Вход")) {
            sendMessage("Привет, " + customer.getName() + "\n" + Messages.ENTRY.getText());
        }
        //тест
        else if(inMessage.getText().contains("Кто "+"я")){
            if(customer.getTgUsername().equals("SaprykinAV")){
                sendMessage("Жмых");
            }
            else if (customer.getTgUsername().equals("Zlary")) {
                sendMessage("Жмыха");
            }
        }
        else {
            sendMessage("напиши что-нибудь другое");
        }
    }
    //функция авторизации и ее проверки.
    public void authorization(Message inMessage) throws NoSuchElementException, TelegramApiException {
        try {
            Optional<Customer> customerFromDB = botService.authorization(inMessage);

            if (customerFromDB.isEmpty()) {
                sendMessage(Messages.AUTHORIZATIONERROR.getText());
                throw new NoSuchElementException("Customer not found");
            }
            customer = customerFromDB.get();
            chat_id = inMessage.getChatId();
            input(inMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    //функция отправки сообщения
    public void sendMessage(String text) throws TelegramApiException {
        try {
            SendMessage outMessage = new SendMessage();
            outMessage.setChatId(chat_id);
            outMessage.setText(text);
            execute(outMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void help() throws TelegramApiException {
        sendMessage(Messages.HELP.getText());
    }
}
