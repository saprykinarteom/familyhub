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
import ru.saprykinav.familyhub.entity.Item;
import ru.saprykinav.familyhub.entity.Wishlist;
import ru.saprykinav.familyhub.service.CustomerService;

import java.io.Serializable;
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
                    case "buy":
                        sendMessage(inMessage, Messages.ADDBUY.getText());
                        setCondition(inMessage, 1);
                        break;
                    case "Список покупок" :
                        //для нескольких листов, в текущей версии не используется
                        //sendMessage(inMessage, botWishlistService.loadWishlists(getCustomerByChat(inMessage)));
                        //sendMessage(inMessage, Messages.SHOP.getText());
                        setWishlist(inMessage, (Wishlist) botWishlistService.loadWishlist("1"));
                        sendMessage(inMessage, getWishlist(inMessage).toString());
                        sendMessage(inMessage, Messages.CREATEITEM.getText());
                        setCondition(inMessage, 6);
                        break;
                    case "В магазине" :
                    case "/shop":
                        //sendMessage(inMessage,botWishlistService.loadWishlists(getCustomerByChat(inMessage)));
                        //sendMessage(inMessage, Messages.SHOP.getText());
                        setWishlist(inMessage, (Wishlist) botWishlistService.loadWishlist("1"));
                        setCondition(inMessage, 4);
                        sendMessage(inMessage, Messages.WISHLIST.getText());
                        sendMessage(inMessage, getWishlist(inMessage).toString());
                        break;
                    case "999":
                        setCondition(inMessage, 999);
                        sendMessage(inMessage, Messages.ADMIN.getText());
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
    //страница семьи
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
                break;
    //выбор листа покупок, находясь в магазине(в этой версии не используется)
            case 3:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        Serializable answer = botWishlistService.loadWishlist(inMessage.getText());
                        if(answer instanceof Wishlist){
                            setWishlist(inMessage, (Wishlist) answer);
                            sendMessage(inMessage, answer.toString());
                            setCondition(inMessage, 4);
                            sendMessage(inMessage, Messages.WISHLIST.getText());
                        }
                        else{
                            sendMessage(inMessage, (String) answer);
                        }
                        break;
                }
                break;
    //закрытие пункта в списке
            case 4:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    case "Закончить":
                        sendMessage(inMessage, botWishlistService.loadWishlist(getWishlist(inMessage)));
                        sendNotificationToFamily("Что-то куплено из списка. Осталось купить:  " + getWishlist(inMessage).toString());
                        sendMessage(inMessage, "Добавим покупку в бюджет? Напиши сколько она стоила ");
                        setCondition(inMessage, 1);
                        break;
                    default:
                        sendMessage(inMessage, botWishlistService.closeItem(inMessage.getText()));
                        sendMessage(inMessage, botWishlistService.loadWishlist(getWishlist(inMessage)));
                        break;
                }
                break;

    //выбор листа покупок(не используется в этой версии)
            case 5:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        Serializable answer = botWishlistService.loadWishlist(inMessage.getText());
                        if(answer instanceof Wishlist){
                            setWishlist(inMessage, (Wishlist) answer);
                            sendMessage(inMessage, answer.toString());
                            setCondition(inMessage, 6);
                            sendMessage(inMessage, Messages.CREATEITEM.getText());
                        }
                        else{
                            sendMessage(inMessage, (String) answer);
                        }
                        break;
                }
                break;
    //ввод имени покупки
            case 6:
                switch (inMessage.getText()) {
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    case "Закончить":
                        sendMessage(inMessage, botWishlistService.loadWishlist(getWishlist(inMessage)));
                        sendNotificationToFamily("Обновлен лист покупок " + getWishlist(inMessage).toString());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        setItem(inMessage, botWishlistService.createItem(inMessage.getText()));
                        sendMessage(inMessage, Messages.ADDQUANTITYITEM.getText());
                        setCondition(inMessage, 7);
                        break;
                }
            break;
    //ввод количества покупки
            case 7:
                switch ((inMessage.getText())){
                    case "Отмена":
                        sendMessage(inMessage, Messages.CANCEL.getText());
                        setCondition(inMessage, 0);
                        break;
                    default:
                        sendMessage(inMessage, botWishlistService.createItem(getItem(inMessage), getWishlist(inMessage), inMessage.getText()));
                        sendMessage(inMessage, Messages.CONTINUECREATEITEMS.getText());
                        setCondition(inMessage, 6);
                        break;
                }
                break;
    //admin
            case 999:
                switch ((inMessage.getText())) {
                    case "addList":
                        setCondition(inMessage,9991);
                        sendMessage(inMessage, "name");
                        break;
                    default:
                        setCondition(inMessage, 0);
                        break;
                }
                break;
    //add list
            case 9991:
                switch ((inMessage.getText())) {
                    case "Отмена":
                        setCondition(inMessage,0);
                        break;
                    default:
                        sendMessage(inMessage, botWishlistService.addWishlist(getCustomerByChat(inMessage), inMessage.getText()).toString());
                        setCondition(inMessage, 0);
                        break;
                }
                break;
            default:
                setCondition(inMessage, 0);
                break;
        }
    }
    //функция авторизации и ее проверки.
    public void authorization(Message inMessage) throws NoSuchElementException, TelegramApiException {
        try {
            Customer customerFromDB = botService.authorization(inMessage);
            state.put(inMessage.getChatId(), new ChatInfo(0,customerFromDB));
            input(inMessage);
            System.out.println(inMessage.getChatId());
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
    //оповещение всем членам семьи 1 (хардкод на айди чатов)
    public void sendNotificationToFamily (String text) throws TelegramApiException{
        try {
            SendMessage outMessage = new SendMessage();
            outMessage.setText(text);
            outMessage.setChatId((long) 338011120);
            execute(outMessage);
            outMessage.setChatId((long) 139381254);
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
    public void setWishlist(Message inMessage, Wishlist wishlist){
        state.get(inMessage.getChatId()).setWishlist(wishlist);
    }
    public void setItem(Message inMessage, Item item){
        state.get(inMessage.getChatId()).setItem(item);
    }
    public Wishlist getWishlist(Message inMessage) {
        return state.get(inMessage.getChatId()).getWishlist();
    }
    public Item getItem(Message inMessage) {
        return state.get(inMessage.getChatId()).getItem();
    }
}
