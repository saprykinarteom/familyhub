package ru.saprykinav.familyhub.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.saprykinav.familyhub.entity.Customer;
import ru.saprykinav.familyhub.entity.Item;
import ru.saprykinav.familyhub.entity.Wishlist;

@Component
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ChatInfo {
    private Integer condition;
    private Customer customer;
    private Wishlist wishlist = new Wishlist();
    private Item item = new Item();

    public ChatInfo(int condition, Customer customer){
        this.condition = condition;
        this.customer = customer;

    }
}
