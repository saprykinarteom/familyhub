package ru.saprykinav.familyhub.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Role implements GrantedAuthority {
    private Integer id;
    private String role;

    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }


    @Basic
    @Column(name = "role", nullable = false, length = 5)
    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
