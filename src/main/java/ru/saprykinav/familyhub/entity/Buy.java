package ru.saprykinav.familyhub.entity;

import jdk.vm.ci.meta.Local;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Buy {
    private Long id;
    private Long customerId;
    private BigDecimal price;
    private LocalDate date;

    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "customer_id", nullable = false)
    public Long getCustomerId() {
        return customerId;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public BigDecimal getPrice() {
        return price;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public LocalDate getDate() {
        return date;
    }
}
