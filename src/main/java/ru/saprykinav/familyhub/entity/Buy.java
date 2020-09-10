package ru.saprykinav.familyhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date")
    private LocalDate date;

    public Buy(Customer customer, BigDecimal price){
        this.customer = customer;
        this.price = price;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return  customer.getName() +
                " " + price +
                " " + date;
    }
}
