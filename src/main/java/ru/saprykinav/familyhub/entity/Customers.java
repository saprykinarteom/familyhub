package ru.saprykinav.familyhub.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Customers {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private Collection<Buy> buysById;
    private Collection<Credit> creditsById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 25)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customers customers = (Customers) o;

        if (id != null ? !id.equals(customers.id) : customers.id != null) return false;
        if (username != null ? !username.equals(customers.username) : customers.username != null) return false;
        if (name != null ? !name.equals(customers.name) : customers.name != null) return false;
        if (password != null ? !password.equals(customers.password) : customers.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "customersByCustomerId")
    public Collection<Buy> getBuysById() {
        return buysById;
    }

    public void setBuysById(Collection<Buy> buysById) {
        this.buysById = buysById;
    }

    @OneToMany(mappedBy = "customersByCustomerId")
    public Collection<Credit> getCreditsById() {
        return creditsById;
    }

    public void setCreditsById(Collection<Credit> creditsById) {
        this.creditsById = creditsById;
    }
}
