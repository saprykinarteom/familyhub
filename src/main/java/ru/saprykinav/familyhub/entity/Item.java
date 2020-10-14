package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Item implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Wishlist wishlist;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;

    //0 - нужно купить, 1 - куплено
    @Column(name = "state")
    private Integer state;

    public Item(Wishlist wishlist, String name, String quantity) {
        this.wishlist = wishlist;
        this.name = name;
        this.quantity = quantity;
        this.state = 0;
    }

    @Override
    public String toString() {
        return  id +
                " " +  name + " " + quantity + '\'' ;
    }
}
