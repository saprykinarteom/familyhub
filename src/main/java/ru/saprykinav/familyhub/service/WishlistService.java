package ru.saprykinav.familyhub.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Wishlist;
import ru.saprykinav.familyhub.repository.WishlistRepository;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ItemService itemService;

    public WishlistService (WishlistRepository wishlistRepository, ItemService itemService){
        this.itemService = itemService;
        this.wishlistRepository = wishlistRepository;
    }



}
