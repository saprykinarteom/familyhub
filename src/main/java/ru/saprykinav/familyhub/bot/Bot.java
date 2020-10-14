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
import ru.saprykinav.familyhub.bot.service.BotFamilyService;
import ru.saprykinav.familyhub.bot.service.BotWishlistService;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.service.CustomerService;

import java.time.LocalDate;
import java.util.*;

@Component
public class Bot extends TelegramLongPollingBot {


    private final BotService botService;
    private final CustomerService customerService;
    private final BotBuyService botBuyService;
    private final BotFamilyService botFamilyService;
    private final BotWishlistService botWishlistService;

    public Bot(BotService botService, CustomerService customerService, BotBuyService botBuyService, BotFamilyService botFamilyService, BotWishlistService botWishlistService){
        this.botService = botService;
        this.customerService = customerService;
        this.botBuyService = botBuyService;
        this.botFamilyService = botFamilyService;
        this.botWishlistService = botWishlistService;
    }

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
                        sendMessage(inMessage, Messages.HELP.getText());
                        break;
                    case "Семья" :
                        sendMessage(inMessage, botService.getFamilyInfo(getCustomerByChat(inMessage)));
                        sendMessage(inMessage, Messages.HOMEFAMILY.getText());
                        setCondition(inMessage, 2);
                        break;
                    case "Добавить покупку" :
                        sendMessage(inMessage, Messages.ADDBUY.getText());
                        setCondition(inMessage, 1);
                        break;
                    case "В магазине" :
                    case "/shop":
                        sendMessage(inMessage,botWishlistService.loadWishlists(getCustomerByChat(inMessage)));
                        sendMessage(inMessage, Messages.SHOP.getText());
                        setCondition(inMessage, 3);
                        break;
                    default : sendMessage(inMessage, Messages.SENDELSE.getText());
                }
                break;
    //добавление покупки
            case 1:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        sendMessage(inMessage, botBuyService.addBuy(inMessage.getText(), getCustomerByChat(inMessage)));
                        setCondition(inMessage, 0);
                        break;
                }
                break;
            case 2:
                switch (inMessage.getText()) {
                    case "Ок":
                        sendMessage(inMessage, Messages.HOME.getText());
                        setCondition(inMessage, 0);
                        break;
                    case "Покупки":
                        sendMessage(inMessage, botBuyService.getBuyByLastMonth(getCustomerByChat(inMessage)));
                        sendMessage(inMessage, Messages.HOME.getText());
                        setCondition(inMessage, 0);
                        break;
                    case "Заплатили":
                        sendMessage(inMessage, botFamilyService.setLastPayDay(getCustomerByChat(inMessage).getFamily().getId(), LocalDate.now()));
                        setCondition(inMessage, 0);
                        break;
                    case "Пора платить":
                        sendMessage(inMessage, botService.getPayInfo(getCustomerByChat(inMessage)));
                        sendMessage(inMessage, Messages.HOME.getText());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        sendMessage(inMessage, Messages.SENDELSE.getText());
                        setCondition(inMessage, 0);
                        break;
                }
            case 3:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        sendMessage(inMessage, botWishlistService.loadWishlist(inMessage.getText()));
                        setWhishlistId(inMessage);
                        sendMessage(inMessage, Messages.WISHLIST.getText());
                        setCondition(inMessage, 4);
                        break;
                }
                break;
            case 4:
                switch (inMessage.getText()) {
                    case "Назад":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 3);
                        break;
                    default:
                        sendMessage(inMessage, botWishlistService.closeItem(inMessage.getText()));
                        sendMessage(inMessage, botWishlistService.loadWishlist(state.get(inMessage.getChatId()).getWhishlistId()));
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
            Customer customerFromDB = botService.authorization(inMessage);
            state.put(inMessage.getChatId(), new ChatInfo(0,customerFromDB));
            input(inMessage);
        }
        catch (TelegramApiException | RuntimeException | NotFoundException e){
            e.printStackTrace();
            sendMessage(inMessage, Messages.AUTHORIZATIONERROR.getText());
        }
    }
    //функция отправки сообщения
    public void sendMessage(Message inMessage, String text) throws TelegramApiException {
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
    public void setWhishlistId(Message inMessage){
        state.get(inMessage.getChatId()).setWhishlistId(inMessage.getText());
    }
}
