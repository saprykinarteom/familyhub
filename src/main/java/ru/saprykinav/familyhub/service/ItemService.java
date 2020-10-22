package ru.saprykinav.familyhub.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Item;
import ru.saprykinav.familyhub.entity.Wishlist;
import ru.saprykinav.familyhub.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Item save (Wishlist wishlist, String name, String quantity) {
        return itemRepository.save(new Item(wishlist, name, quantity));
    }

    public void toClose (Long id){
        Item itemFromDb = itemRepository.findById(id).orElseThrow(RuntimeException::new);
        itemFromDb.setState(1);
        itemRepository.save(itemFromDb);
    }

    public Item loadById( Long id){
        return itemRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Item> loadAllByOwnerIdAndState(Long wishlistId, Integer state){
        return itemRepository.findAllByWishlistIdAndState(wishlistId, state);
    }


}
