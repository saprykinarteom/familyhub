package ru.saprykinav.familyhub.bot.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.service.ItemService;
import ru.saprykinav.familyhub.service.WishlistService;

import java.math.BigDecimal;

@Service
public class BotWishlistService {
    private final WishlistService wishlistService;
    private final ItemService itemService;

    public BotWishlistService(WishlistService wishlistService, ItemService itemService){
        this.wishlistService = wishlistService;
        this.itemService = itemService;
    }

    public String loadWishlists(Customer customer){
        return wishlistService.loadByOwnerId(customer.getFamily().getId()).toString();
    }
    public String loadWishlist(String wishlistId) throws NumberFormatException{
        try{
            Long id = Long.valueOf(wishlistId);
            return wishlistService.loadById(id).toString();
        }
        catch (NumberFormatException e){
            return "Ошибка! Введено не число";
        }
    }
    public String closeItem(String itemId) throws NumberFormatException{
        try{
            Long id = Long.valueOf(itemId);
            return "Ok";
        }
        catch (NumberFormatException e){
            return "Ошибка! Введено не число";
        }
    }
}
