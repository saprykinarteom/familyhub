package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Buy implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date")
    private LocalDate date;


    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }
}
