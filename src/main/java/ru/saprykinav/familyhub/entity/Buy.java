package ru.saprykinav.familyhub.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

@Entity
public class Buy {
    private Long id;
    private Integer customerId;
    private BigInteger price;
    private Date date;
    private Customers customersByCustomerId;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_id", nullable = false)
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Buy buy = (Buy) o;

        if (id != null ? !id.equals(buy.id) : buy.id != null) return false;
        if (customerId != null ? !customerId.equals(buy.customerId) : buy.customerId != null) return false;
        if (price != null ? !price.equals(buy.price) : buy.price != null) return false;
        if (date != null ? !date.equals(buy.date) : buy.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    public Customers getCustomersByCustomerId() {
        return customersByCustomerId;
    }

    public void setCustomersByCustomerId(Customers customersByCustomerId) {
        this.customersByCustomerId = customersByCustomerId;
    }
}
