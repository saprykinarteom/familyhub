package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saprykinav.familyhub.entity.Buy;
import ru.saprykinav.familyhub.entity.Credit;

import java.time.LocalDate;
import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findAllByCustomerId (Long customerId);
    List<Credit> findAllByCustomerIdAndDateBetween (Long customerId, LocalDate dateFrom, LocalDate dateTo);
}
