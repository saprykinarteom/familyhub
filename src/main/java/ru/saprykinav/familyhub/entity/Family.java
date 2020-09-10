package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal lastMonthBuys;

    @Column(name = "mandatory_spending")
    private BigDecimal mandatorySpending;

    public Family(Long id) {
        this.id = id;
    }

    @Transient
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Customer> customers;

    @Override
    public String toString() {
        return  "Покупки за последний месяц " + lastMonthBuys +
                "Плата за квартиру" + mandatorySpending +
                "Мы" + customers;
    }
}
