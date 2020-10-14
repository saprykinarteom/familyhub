package ru.saprykinav.familyhub.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Family;
import ru.saprykinav.familyhub.entity.Wishlist;
import ru.saprykinav.familyhub.repository.WishlistRepository;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ItemService itemService;

    public WishlistService (WishlistRepository wishlistRepository, ItemService itemService){
        this.itemService = itemService;
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist save(Family family, String name) {
        return wishlistRepository.save(new Wishlist(family, name));
    }
    public Wishlist loadById(Long id){
        Wishlist wishlistFromDb = wishlistRepository.findById(id).orElseThrow(RuntimeException::new);
        wishlistFromDb.setItems(itemService.loadAllByOwnerIdAndState(wishlistFromDb.getId(), 0));
        return wishlistFromDb;
    }
    public List<Wishlist> loadByOwnerId(Long id){
        return wishlistRepository.findAllByOwnerId(id);
    }

}
