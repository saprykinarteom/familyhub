package ru.saprykinav.familyhub.bot;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.saprykinav.familyhub.bot.service.BotBuyService;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.CustomerService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    BotService botService;
    @Autowired
    CustomerService customerService;
    @Autowired
    BotBuyService botBuyService;

    private Long chat_id = Long.valueOf(0);
    private Customer customer;
    private int state = 0;

    public void onUpdateReceived(Update update) {
        try {
            //проверяем есть ли сообщение и текстовое ли оно
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем объект входящего сообщения
                Message inMessage = update.getMessage();
                System.out.println(inMessage.getText());
                //проверяем авторизован ли пользователь
                if(!inMessage.getChatId().equals(chat_id)) {
                    authorization(inMessage);
                }
                //обрабатываем команду
                //
                else{
                    input(inMessage);
                }
            }
        } catch (TelegramApiException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "@family_hub_bot";
    }

    public String getBotToken() {
        return "1372106827:AAGa_JAk-eMRfWnYQeBWkUhljuSOCiXyejc";
    }

    public void input(Message inMessage) throws TelegramApiException, NotFoundException {
        switch (state){
            case 0:
                switch (inMessage.getText()) {
                    case "/help" :
                    case "Помощь" :
                        sendMessage(Messages.HELP.getText());
                        break;
                    case "Вход" : sendMessage("Привет, " + customer.getName() + "\n" + Messages.ENTRY.getText());
                        break;
                    case "Семья" :
                        sendMessage(botService.getFamilyInfo(customer));
                        break;
                    case "Добавить покупку" :
                        sendMessage(Messages.ADDBUY.getText());
                        state = 1;
                        break;
                    case "Кто я" : sendMessage(customer.getName());
                        break;
                    default : sendMessage(Messages.SENDELSE.getText());
                }
                break;
            case 1:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(Messages.CANCEL.getText());
                        state = 0;
                        break;
                    default:
                        sendMessage(botBuyService.addBuy(inMessage.getText(),customer));
                        state = 0;
                        break;
                }
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
        catch (TelegramApiException | NotFoundException e){
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
}
