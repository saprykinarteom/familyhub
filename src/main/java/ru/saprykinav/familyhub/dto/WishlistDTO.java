package ru.saprykinav.familyhub.dto;

import lombok.Data;
import ru.saprykinav.familyhub.entity.Item;

import java.util.List;

@Data
public class WishlistDTO {
    private Long id;
    private String name;
    private List<Item> item;

    @Override
    public String toString() {
        return name + '\''  +
                item;
    }
}
