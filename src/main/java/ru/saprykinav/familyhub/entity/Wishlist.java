package ru.saprykinav.familyhub.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Wishlist implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Transient
    @OneToMany(mappedBy = "wishlist", fetch = FetchType.EAGER)
    private List<Item> items = new LinkedList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Family owner;

    @Column(name="name")
    private String name;

    public Wishlist(Family owner, String name){
        this.owner = owner;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " " + name + '\''  +
                items;
    }
}
