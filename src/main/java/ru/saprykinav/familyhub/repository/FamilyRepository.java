package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saprykinav.familyhub.entity.Family;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}
