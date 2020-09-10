package ru.saprykinav.familyhub.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.saprykinav.familyhub.entity.Customer;

@Component
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ChatInfo {
    private Integer condition;
    private Customer customer;

    public ChatInfo(int condition, Customer customer){
        this.condition = condition;
        this.customer = customer;
    }
}
