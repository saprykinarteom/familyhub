package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Order implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Family family;

    @Column(name = "item")
    private String item;

    @Column(name = "quantity")
    private String quantity;

    //0 - нужно купить, 1 - куплено
    @Column(name = "state")
    private Integer state;

    public Order(Family family, String item, String quantity) {
        this.family = family;
        this.item = item;
        this.quantity = quantity;
    }

}
