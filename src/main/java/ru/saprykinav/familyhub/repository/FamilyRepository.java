package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saprykinav.familyhub.entity.Family;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    List<Family> findAllByCustomerId (Long customerId);
}
