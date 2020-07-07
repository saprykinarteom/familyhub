package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Family implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "last_month_buys")
    private Long lastMonthBuys;

    @Column(name = "last_month_credits")
    private Long lastMonthCredits;

    @Column(name = "mandatory_spending")
    private Long mandatorySpending;

    public Family(Long id) {
        this.id = id;
    }

    @Transient
    @ManyToMany(mappedBy = "families")
    private Set<Customer> customers;

}
