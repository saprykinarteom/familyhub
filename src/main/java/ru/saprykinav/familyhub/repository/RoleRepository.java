package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.saprykinav.familyhub.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
