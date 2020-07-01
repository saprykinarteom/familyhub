package ru.saprykinav.familyhub.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
public class Family {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Transient
    @ManyToMany(mappedBy = "families")
    private Set<Customer> customers;

}
