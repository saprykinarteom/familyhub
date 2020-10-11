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
    private Family family;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;

    //0 - нужно купить, 1 - куплено
    @Column(name = "state")
    private Integer state;

    public Item(Family family, String name, String quantity) {
        this.family = family;
        this.name = name;
        this.quantity = quantity;
    }

}
