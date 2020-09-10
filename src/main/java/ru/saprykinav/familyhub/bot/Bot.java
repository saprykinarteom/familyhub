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

import java.util.*;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    BotService botService;
    @Autowired
    CustomerService customerService;
    @Autowired
    BotBuyService botBuyService;

    private Map<Long, ChatInfo> state = new HashMap<>();

    public void onUpdateReceived(Update update) {
        try {
            //проверяем есть ли сообщение и текстовое ли оно
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем объект входящего сообщения
                Message inMessage = update.getMessage();
                System.out.println(inMessage.getText());
                //проверяем авторизован ли пользователь
                if(!state.containsKey(inMessage.getChatId())) {
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
        switch (getConditionByChat(inMessage)){
            case 0:
                switch (inMessage.getText()) {
                    case "/help" :
                    case "Помощь" :
                        sendMessage(Messages.HELP.getText(), inMessage);
                        break;
                    case "Вход" : sendMessage("Привет, " + getCustomerByChat(inMessage).getName() + "\n" + Messages.ENTRY.getText(), inMessage);
                        break;
                    case "Семья" :
                        sendMessage(botService.getFamilyInfo(getCustomerByChat(inMessage)), inMessage);
                        break;
                    case "Добавить покупку" :
                        sendMessage(Messages.ADDBUY.getText(), inMessage);
                        setCondition(inMessage, 1);
                        break;
                    default : sendMessage(Messages.SENDELSE.getText(), inMessage);
                }
                break;
    //добавление покупки
            case 1:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(Messages.CANCEL.getText(), inMessage);
                        setCondition(inMessage, 0);
                        break;
                    default:
                        sendMessage(botBuyService.addBuy(inMessage.getText(), getCustomerByChat(inMessage)), inMessage);
                        setCondition(inMessage, 0);
                        break;
                }
                break;
            default:
                setCondition(inMessage, 0);
        }
    }
    //функция авторизации и ее проверки.
    public void authorization(Message inMessage) throws NoSuchElementException, TelegramApiException {
        try {
            Optional<Customer> customerFromDB = botService.authorization(inMessage);

            if (customerFromDB.isEmpty()) {
                sendMessage(Messages.AUTHORIZATIONERROR.getText(), inMessage);
                throw new NoSuchElementException("Customer not found");
            }
            state.put(inMessage.getChatId(), new ChatInfo(0,customerFromDB.get()));
            input(inMessage);
        }
        catch (TelegramApiException | NotFoundException e){
            e.printStackTrace();
        }
    }
    //функция отправки сообщения
    public void sendMessage(String text, Message inMessage) throws TelegramApiException {
        try {
            SendMessage outMessage = new SendMessage();
            outMessage.setChatId(inMessage.getChatId());
            outMessage.setText(text);
            execute(outMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public Customer getCustomerByChat(Message inMessage){
        return state.get(inMessage.getChatId()).getCustomer();
    }
    public Integer getConditionByChat(Message inMessage) {
        return state.get(inMessage.getChatId()).getCondition();
    }
    public void setCondition(Message inMessage, int condition){
        state.get(inMessage.getChatId()).setCondition(condition);
    }
}
