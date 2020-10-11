package ru.saprykinav.familyhub.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.entity.Item;
import ru.saprykinav.familyhub.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Item saveItem (Item item) {
        return itemRepository.save(item);
    }

    public Item findById( Long id){
        return itemRepository.findById(id).orElseThrow(RuntimeException::new);
    }



}
