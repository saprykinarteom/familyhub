package ru.saprykinav.familyhub.service;

import org.springframework.stereotype.Service;
import ru.saprykinav.familyhub.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }


}
