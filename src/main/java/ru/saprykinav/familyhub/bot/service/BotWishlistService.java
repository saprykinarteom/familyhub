package ru.saprykinav.familyhub.bot.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.entity.Item;
import ru.saprykinav.familyhub.entity.Wishlist;
import ru.saprykinav.familyhub.service.ItemService;
import ru.saprykinav.familyhub.service.WishlistService;

import java.io.Serializable;
import java.math.BigDecimal;

@Service
public class BotWishlistService {
    private final WishlistService wishlistService;
    private final ItemService itemService;

    public BotWishlistService(WishlistService wishlistService, ItemService itemService){
        this.wishlistService = wishlistService;
        this.itemService = itemService;
    }
    
    public Wishlist addWishlist(Customer customer, String name){
        return wishlistService.save(customer.getFamily(), name);
    }

    public String loadWishlists(Customer customer){
        return wishlistService.loadByOwnerId(customer.getFamily().getId()).toString();
    }
    public Serializable loadWishlist(String wishlistId) throws NumberFormatException{
        try{
            Long id = Long.valueOf(wishlistId);
            return wishlistService.loadById(id);
        }
        catch (NumberFormatException e){
            return "Ошибка! Введено не число";
        }
    }
    public String loadWishlist(Wishlist wishlist){
        return wishlistService.loadById(wishlist.getId()).toString();
    }
    public String closeItem(String itemId) throws NumberFormatException{
        try{
            Long id = Long.valueOf(itemId);
            itemService.toClose(id);
            return "Ok";
        }
        catch (NumberFormatException e){
            return "Ошибка! Введено не число";
        }
    }
    public Item createItem(String name){
        return new Item(name);
    }
    public String createItem(Item item, Wishlist wishlist, String quantity){
        itemService.save(wishlist, item.getName(),quantity);
        return "ок. добавлена запись \n" +
                item.getName() + " " + quantity;
    }
}
