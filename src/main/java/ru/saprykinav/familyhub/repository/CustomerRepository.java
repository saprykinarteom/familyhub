package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.saprykinav.familyhub.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByTgUsername(@Param("tg_username") String tgUsername);
    List<Customer> findAllByFamilyId(Long familyId);
}
