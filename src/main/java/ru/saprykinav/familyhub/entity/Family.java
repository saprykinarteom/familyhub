package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

    @Transient
    private BigDecimal sumBuysAfterLastPayDay;

    @Column(name = "mandatory_spending")
    private BigDecimal mandatorySpending;

    @Column(name = "last_pay_day")
    private LocalDate lastPayDay;

    public Family(Long id) {
        this.id = id;
    }

    @Transient
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Customer> customers;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Wishlist> wishlists;

    @Override
    public String toString() {
        return  "Покупки за последний месяц " + sumBuysAfterLastPayDay +
                "Плата за квартиру" + mandatorySpending +
                "Мы" + customers;
    }
}
