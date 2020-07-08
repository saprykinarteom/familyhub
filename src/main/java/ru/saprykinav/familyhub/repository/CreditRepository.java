package ru.saprykinav.familyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.saprykinav.familyhub.entity.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findAllByCustomerId (Long customerId);
    List<Credit> findAllByCustomerIdAndDateBetween (Long customerId, LocalDate dateFrom, LocalDate dateTo);
    @Query(value = "select sum(Credit.price) from Credit where (Credit.customer_id = :customer_id and (Credit.date between :date_from and :date_to))", nativeQuery = true)
    Optional<BigDecimal> findSumPriceAllByCustomerIdAndDateBetween (@Param("customer_id") Long customerId, @Param("date_from") LocalDate dateFrom, @Param("date_to") LocalDate dateTo);
}
