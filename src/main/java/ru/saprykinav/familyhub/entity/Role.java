package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Role implements GrantedAuthority, Serializable {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "role")
    private String role;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Customer> users;

    public Role(Integer id, String name) {
        this.id = id;
        this.role = name;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
