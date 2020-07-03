package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saprykinav.familyhub.entity.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
